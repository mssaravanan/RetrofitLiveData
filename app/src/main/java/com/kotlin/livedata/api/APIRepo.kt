package com.kotlin.livedata.api

import com.kotlin.livedata.LiveDataRetrofit
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

import com.kotlin.livedata.constants.Constants
import com.kotlin.livedata.util.Resource
import com.kotlin.livedata.utility.Utility
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class APIRepo {

    /**
     * Retrofit - default response/ failure
     */
    companion object ObjAPICallBack : retrofit2.Callback<ResponseBody> {
        var apiResponse = MutableLiveData<Resource<ResponseBody>>()
        override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            if (!Utility.isInternetConnectedCheck(LiveDataRetrofit.appContext)) {
                apiResponse.value = Resource.errorNoNetwork("No Network", null)
            }else {
                apiResponse.value = Resource.error(t!!.localizedMessage, null)
            }
        }

        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
            apiResponse?.value = Resource.success(response!!.body())
        }
    }



    /**
     * Call login API Service
     * @param requestBody
     *
     */
    fun loginRepo(requestBody: RequestBody): LiveData<Resource<ResponseBody>> {
        if (!Utility.isInternetConnectedCheck(LiveDataRetrofit.appContext)) {
            apiResponse.value = Resource.errorNoNetwork("No Network", null)
            return apiResponse
        }
        apiResponse.value = Resource.loading(null)
        RetrofitFactory.createService(APIService::class.java, Constants.BASE_URL).Post(Constants.POST_SIGN_IN_URL, requestBody).enqueue(ObjAPICallBack)
        return apiResponse
    }





}
