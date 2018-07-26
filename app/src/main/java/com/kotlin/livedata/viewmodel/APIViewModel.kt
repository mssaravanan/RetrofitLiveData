package com.kotlin.livedata.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.kotlin.livedata.api.APIRepo
import com.kotlin.livedata.util.AbsentLiveData
import com.kotlin.livedata.util.Resource
import okhttp3.RequestBody

open class APIViewModel:ViewModel() {

    val _requestBody = MutableLiveData<APIRequestData>()

    var APIRepo: APIRepo = APIRepo()



    /**
     * function return the user login details
     *
     * @return LiveData<Resource<LoginResponse>>
     *
     */
    inline fun <reified T> getResponse(): LiveData<Resource<T>> {

        var response: LiveData<Resource<T>> = Transformations.switchMap(_requestBody) { _requestBody ->
            if (_requestBody == null) {
                AbsentLiveData.create()
            }
            else {
                APIRepo.postAPIRepo<T>(_requestBody)
            }
        }
        return  response

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