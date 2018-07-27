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

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.kotlin.livedata.model.SearchData

class PageViewModel <T>(private val repository: PagePostRepository) : ViewModel() {

    //It store the search data
    private var searchData = MutableLiveData<SearchData>()

    // It return the Repo value as normal listing data
    val repoResult = map(searchData, {
        repository.postsOfSearch<T>(it)
    })


  /*

   Need Function for results pls un comment code

   fun <T> repoResults() :LiveData<Listing<T>>{
    return map(searchData, {
        repository.postsOfSearch<T>(it) }) as LiveData<Listing<T>>
    }

    fun <T> postResult():LiveData<PagedList<T>>{
        return  switchMap(repoResults<T>(), {
            it.pagedList
        })!! as LiveData<PagedList<T>>
    }*/

    //Paged Live data variable from listing
    val posts = switchMap(repoResult, {
        it.pagedList

    })!!

    //If failed we need to reload
    val networkState = switchMap(repoResult, { it.networkState })!!

    //Pull to refresh
    val refreshState = switchMap(repoResult, { it.refreshState })!!

    //If search data found or not
    val dataState = switchMap(repoResult, { it.dataState })!!

    //call pull to refresh
    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    //Set search string in mutable variable for iitialize the search
    fun showSearchData(subreddit: SearchData): Boolean {
        if (searchData.value == subreddit) {
            return false
        }
        searchData.value = subreddit
        return true
    }


    //If network failure or any othe failure is occur we need to call again using failure
    fun retry() {
        val listing = repoResult?.value
        listing?.retry?.invoke()
    }

    fun currentSearchText(): String? = searchData.value?.searchText
}