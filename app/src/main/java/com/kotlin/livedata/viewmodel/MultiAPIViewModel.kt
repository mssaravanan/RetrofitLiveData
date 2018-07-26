package com.kotlin.livedata.viewmodel

import android.arch.lifecycle.*
import com.kotlin.livedata.api.APIRepo
import com.kotlin.livedata.util.AbsentLiveData
import com.kotlin.livedata.util.Resource
import okhttp3.RequestBody


open class MultiAPIViewModel:ViewModel() {

    lateinit var _requestBody: MutableLiveData<List<APIRequestData>>

    var APIRepo: APIRepo = APIRepo()



    /**
     * function return the user login details
     *
     * @return LiveData<Resource<LoginResponse>>
     *
     */
    inline fun <reified T> getResponses(): MediatorLiveData<Resource<T>> {

        val liveDataMerger = MediatorLiveData<Resource<T>>()
        /*lateinit var reqData:MutableLiveData<APIRequestData>
        reqData.value=request*/

        for(request:APIRequestData in _requestBody.value!!) {
            MutableLiveData<APIRequestData>().apply { value=request }.let { liveDataMerger.addSource(getResponse(it), Observer<Resource<T>> { t -> liveDataMerger.value=t }) }
        }

        return  liveDataMerger
    }


    inline fun <reified T> getResponse(requestBody: MutableLiveData<APIRequestData>): LiveData<Resource<T>> {

        return Transformations.switchMap(requestBody) { _requestBody ->
            if (_requestBody == null) {
                AbsentLiveData.create()
            } else {
                APIRepo.postAPIRepo<T>(_requestBody)
            }
        }

    }



    /**
     * set Request Body
     *
     */
    fun setRequestBody(requestBody: MutableLiveData<List<APIRequestData>>){
        if(_requestBody.value!=requestBody.value){
            _requestBody.value=requestBody.value
        }
    }



    /**
     * Retry option to call api again.
     *
     */

    fun retry() {

        _requestBody.value?.let {
            _requestBody.value = it
        }

    }

}