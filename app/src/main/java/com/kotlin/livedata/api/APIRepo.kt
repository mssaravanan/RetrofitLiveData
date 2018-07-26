package com.kotlin.livedata.api

import com.kotlin.livedata.LiveDataRetrofit
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

import com.kotlin.livedata.constants.Constants
import com.kotlin.livedata.model.LoginResponse
import com.kotlin.livedata.model.WineHistoryResponse
import com.kotlin.livedata.util.Resource
import com.kotlin.livedata.utility.Utility
import com.kotlin.livedata.viewmodel.APIRequestData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Response.success

class APIRepo {

    /**
     * Call login API Service
     * @param requestBody
     *
     */
    inline fun <reified T> postAPIRepo(requestBody: APIRequestData): LiveData<Resource<T>> {
        var apiResponse = MutableLiveData<Resource<T>>()
        if (!Utility.isInternetConnectedCheck(LiveDataRetrofit.appContext)) {
            apiResponse.value = Resource.errorNoNetwork("No Network", null)
            return apiResponse
        }
        apiResponse.value = Resource.loading(null)
        getRequestBody<T>(requestBody).onEnqueue(actOnSuccess = {
            apiResponse?.value = Resource.success(it.body() as T)
        }, actOnFailure = {
            if (!Utility.isInternetConnectedCheck(LiveDataRetrofit.appContext)) {
                apiResponse.value = Resource.errorNoNetwork("No Network", null)
            } else {
                apiResponse.value = Resource.error(it!!.localizedMessage, null)
            }
        })
        return apiResponse
    }


    fun <T> Call<T>.onEnqueue(actOnSuccess: (Response<T>) -> Unit, actOnFailure: (t: Throwable?) -> Unit) {
        this.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable?) {
                actOnFailure(t)
            }

            override fun onResponse(call: Call<T>?, response: Response<T>) {
                actOnSuccess(response)
            }
        })
    }


    inline fun <reified T>getRequestBody(apiRequestData: APIRequestData):Call<T>{
        var request: Call<T>
        when(T::class.java.simpleName){
            LoginResponse::class.java.simpleName ->
                request= RetrofitFactory.createService(APIService::class.java, Constants.BASE_URL).postLogin(Constants.POST_SIGN_IN_URL, apiRequestData.requestBody) as Call<T>
            WineHistoryResponse::class.java.simpleName ->
                request = RetrofitFactory.createService(APIService::class.java, Constants.BASE_URL).postWineDetail(Constants.POST_VIEW_WINE_HISTORY_URL, apiRequestData.requestBody,apiRequestData.header) as Call<T>
            else->
                request = RetrofitFactory.createService(APIService::class.java, Constants.BASE_URL).PostSearchWine(Constants.POST_SEARCH_WINE_URL, apiRequestData.requestBody) as Call<T>
        }

       return request;

    }



}
