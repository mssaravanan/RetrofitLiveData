/**
 * @author Saravanan
 *
 *
 */
package com.kotlin.livedata.ui.login

import com.kotlin.livedata.api.APIRepo
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import com.kotlin.livedata.model.LoginResponse
import com.kotlin.livedata.util.AbsentLiveData
import com.kotlin.livedata.util.Resource
import com.kotlin.livedata.viewmodel.APIRequestData
import okhttp3.RequestBody
import okhttp3.ResponseBody

open class LoginViewModel : ViewModel() {

    public var gson: Gson = Gson()
    val _login = MutableLiveData<APIRequestData>()

    var APIRepo: APIRepo = APIRepo()


    /**
     * Directly Call the repo methods
     *
     */
    fun callAPI(login: APIRequestData): LiveData<Resource<ResponseBody>> {
        var liveData: LiveData<Resource<ResponseBody>> = APIRepo.postAPIRepo(login)
        return liveData
    }

    /**
     * function return the user login details
     *
     * @return LiveData<Resource<LoginResponse>>
     *
     */
    inline fun <reified T> getResponse():LiveData<Resource<T>>{

        var response: LiveData<Resource<T>> = Transformations.switchMap(_login) { login ->
            if (login == null) {
                AbsentLiveData.create()
            }
            else {
                APIRepo.postAPIRepo<T>(login)
            }
        }
        return  response

    }


    //Direct call repo with LoginResponse
    var repositories: LiveData<Resource<LoginResponse>>? = null
        get() {
            var response: LiveData<Resource<LoginResponse>> = Transformations.switchMap(_login) { login ->
                if (login == null) {
                    AbsentLiveData.create()
                } else {
                    APIRepo.postAPIRepo<LoginResponse>(login)
                }

            }
            return response;
        }


    /**
     * @param response
     *
     * Convert response body to json pojo
     *
     */
    inline fun <reified T> convertJson(response: Resource<ResponseBody>): Resource<T> {

        if (response.data != null) {
            if (T::class.java.simpleName.equals(LoginResponse::class.java.simpleName)) {
                return Resource<LoginResponse>(response.status, gson.fromJson(response.data.string(), LoginResponse::class.java), response.message) as Resource<T>
            }
        }

        return null as Resource<T>

    }


    /**
     * Set login request body in mutable variable
     *
     * @param login
     */
    fun setLogin(login: APIRequestData?) {
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