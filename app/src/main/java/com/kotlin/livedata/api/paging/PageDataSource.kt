/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kotlin.livedata.api.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.kotlin.livedata.api.APIService
import com.kotlin.livedata.constants.Constants
import com.kotlin.livedata.model.SearchWine
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

/**
 * A data source that uses the before/after keys returned in page requests.
 * <p>
 * See ItemKeyedSubredditDataSource
 */
class PageDataSource<T>(
        private val redditApi: APIService,
        private val subredditName: String,
        private val userId: Int,
        private val retryExecutor: Executor) : PageKeyedDataSource<String, T>() {

    private var pageNumber:Int=1
    private var totalItemSize:Int=0
    private var currentLoadedSize:Int=0
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, T>) {
    }



    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, T>) {

        if(currentLoadedSize<totalItemSize){
            ++pageNumber
        }
        else{
            networkState.postValue(NetworkState.LOADED)
            val error = NetworkState.error("No data found")
            dataState.postValue(error)
            return
        }
        networkState.postValue(NetworkState.LOADING)
        val jsonObject = JSONObject()
        jsonObject.put("searchText", subredditName)
        jsonObject.put("searchUserId", userId)
        //Integer.parseInt(DevicePreferences.getStringDecrypted(mActivity, Constants.USER_ID)))
        jsonObject.put("pageNumber", pageNumber)

        jsonObject.put("pageSize", 10)
        val requestBody = RequestBody.create(MediaType.parse("*/*"), jsonObject.toString())
        redditApi.PostSearchWine(Constants.POST_SEARCH_WINE_URL, requestBody).enqueue(
                object : retrofit2.Callback<SearchWine> {
                    override fun onFailure(call: Call<SearchWine>, t: Throwable) {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                    }

                    override fun onResponse(
                            call: Call<SearchWine>,
                            response: Response<SearchWine>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            val items = data?.dataList ?: null
                            retry = null
                            if(items?.isNotEmpty() == true) {
                                totalItemSize=response?.body()?.Message?.toInt() ?: 0
                                currentLoadedSize=currentLoadedSize+items.size
                                callback.onResult(items as MutableList<T>, pageNumber.toString())
                            }else{
                                val error = NetworkState.error("No data found")
                                dataState.postValue(error)
                            }

                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            retry = {
                                loadAfter(params, callback)
                            }
                            networkState.postValue(
                                    NetworkState.error("error code: ${response.code()}"))
                        }
                    }
                }
        )
    }



    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, T>) {
        val jsonObject = JSONObject()
        jsonObject.put("searchText", subredditName)
        jsonObject.put("searchUserId",userId)
        //Integer.parseInt(DevicePreferences.getStringDecrypted(mActivity, Constants.USER_ID)))
        jsonObject.put("pageNumber", pageNumber)
        jsonObject.put("pageSize", 10)
        val requestBody = RequestBody.create(MediaType.parse("*/*"), jsonObject.toString())

        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            redditApi.PostSearchWine(Constants.POST_SEARCH_WINE_URL,requestBody).enqueue(object :retrofit2.Callback<SearchWine> {
                override fun onFailure(call: Call<SearchWine>?, t: Throwable?) {
                    retry = {
                        loadInitial(params, callback)
                    }
                    networkState.postValue(NetworkState.error(t?.message ?: "unknown err"))

                }

                override fun onResponse(call: Call<SearchWine>?, response: Response<SearchWine>?) {
                    if(response?.isSuccessful==true) {
                        retry = null
                        networkState.postValue(NetworkState.LOADED)
                        initialLoad.postValue(NetworkState.LOADED)
                        val items = response?.body()?.dataList ?: null
                        if (response?.body()?.Status == true && items?.isNotEmpty() == true) {
                            totalItemSize=response?.body()?.Message?.toInt() ?: 0
                            currentLoadedSize=currentLoadedSize+items.size
                            callback.onResult(items as MutableList<T>, "0", "1")
                        } else {
                            val error = NetworkState.error("No data found")
                            dataState.postValue(error)
                        }
                    }else{
                        retry = {
                            loadInitial(params, callback)
                        }
                        networkState.postValue(
                                NetworkState.error("error code: ${response?.code()}"))
                    }
                }

            })



        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }


    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    val dataState = MutableLiveData<NetworkState>()



    fun pagination(){
        ++pageNumber
    }



    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

}