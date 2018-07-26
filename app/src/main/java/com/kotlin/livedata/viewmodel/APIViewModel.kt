package com.kotlin.livedata.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.kotlin.livedata.api.APIRepo
import com.kotlin.livedata.util.AbsentLiveData
import com.kotlin.livedata.util.Resource
import okhttp3.RequestBody
import android.arch.lifecycle.MediatorLiveData



open class APIViewModel:ViewModel() {

    val _requestBody = MutableLiveData<APIRequestData>()


    var APIRepo: APIRepo = APIRepo()



    inline fun <reified T> getResponse(): LiveData<Resource<T>> {

        return Transformations.switchMap(_requestBody) { _requestBody ->
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
    fun setRequestBody(requestBody: APIRequestData?){
        if(_requestBody.value!=requestBody){
            _requestBody.value=requestBody
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