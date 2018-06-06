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

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import com.kotlin.livedata.api.APIService
import com.kotlin.livedata.model.SearchData
import java.util.concurrent.Executor

/**
 * Repository implementation that returns a Listing that loads data directly from network by using
 * the previous / next page keys returned in the query.
 */
class PaginationPageKeyRepository(private val redditApi: APIService,
                                  private val networkExecutor: Executor) : PagePostRepository {
    //Interface method
    override fun <T> postsOfSearch(searchData: SearchData): Listing<T> {

        val sourceFactory = PageDataSourceFactory<T>(redditApi, searchData.searchText,searchData.userId, networkExecutor)


        val livePagedList = LivePagedListBuilder(sourceFactory, 10)
                .setFetchExecutor(networkExecutor)
                .build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }

        return Listing(
                pagedList = livePagedList ,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData, {
                    it.networkState
                }),
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },

                pageNumber = {
                    sourceFactory.sourceLiveData.value?.pagination()
                },
                refreshState = refreshState,

                dataState = Transformations.switchMap(sourceFactory.sourceLiveData, {
                    it.dataState
                }))

    }

}

