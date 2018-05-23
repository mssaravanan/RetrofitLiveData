/**
 * @author Saravanan
 *
 *
 */
package com.kotlin.livedata

import com.kotlin.livedata.api.APIRepo
import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import com.kotlin.livedata.model.LoginResponse
import com.kotlin.livedata.util.AbsentLiveData
import com.kotlin.livedata.util.Resource
import okhttp3.RequestBody
import okhttp3.ResponseBody

open class LoginViewModel : ViewModel() {

    private var gson: Gson = Gson()
    private val _login = MutableLiveData<RequestBody>()

    var APIRepo: APIRepo = APIRepo()


    /**
     * Directly Call the repo methods
     *
     */
    fun callAPI(login: RequestBody): LiveData<Resource<ResponseBody>> {
        var liveData: LiveData<Resource<ResponseBody>> = APIRepo.loginRepo(login)
        return liveData
    }

    /**
     * Variable return the user login details
     *
     * @return LiveData<Resource<LoginResponse>>
     */
    var repositories: LiveData<Resource<LoginResponse>>? = null
        get() {
            var response: LiveData<Resource<ResponseBody>> = Transformations.switchMap(_login) { login ->
                if (login == null) {
                    AbsentLiveData.create()
                } else {
                    APIRepo.loginRepo(login)
                }

            }
            return Transformations.map(response, Function { input ->
                convetJson(input)
            })
        }


    /**
     * @param response
     *
     * Convert response body to json pojo
     *
     */
    fun convetJson(response: Resource<ResponseBody>): Resource<LoginResponse> {
        var loginResponse: LoginResponse? = null
        if (response.data != null) {
            loginResponse = gson.fromJson(response.data.string(), LoginResponse::class.java)
        }
        return Resource<LoginResponse>(response.status, loginResponse, response.message)
    }


    /**
     * Set login request body in mutable variable
     *
     * @param login
     */
    fun setLogin(login: RequestBody?) {
        if (_login.value != login) {
            _login.value = login
        }

    }

    /**
     * Retry option to call api again.
     *
     */

    fun retry() {
        _login.value?.let {
            _login.value = it
        }
    }


}