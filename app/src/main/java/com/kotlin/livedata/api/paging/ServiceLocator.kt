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

import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.kotlin.livedata.api.APIService
import com.kotlin.livedata.api.RetrofitFactory
import com.kotlin.livedata.constants.Constants
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Super simplified service locator implementation to allow us to replace default implementations
 * for testing.
 */
interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator(
                            app = context.applicationContext as Application)
                }
                return instance!!
            }
        }

        /**
         * Allows tests to replace the default implementations.
         */
        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }
    }

    fun getRepository(): PagePostRepository

    fun getNetworkExecutor(): Executor

    fun getRedditApi(): APIService
}

/**
 * default implementation of ServiceLocator that uses production endpoints.
 */
open class DefaultServiceLocator(val app: Application) : ServiceLocator {
    override fun getNetworkExecutor(): Executor {
        return NETWORK_IO
    }

    override fun getRedditApi(): APIService {
        return  api
    }

    // thread pool used for network requests
    @Suppress("PrivatePropertyName")
    private val NETWORK_IO = Executors.newFixedThreadPool(5)


    private val api by lazy {
        RetrofitFactory.createService(APIService::class.java, Constants.BASE_URL)
    }

    override fun getRepository(): PagePostRepository {
        return PaginationPageKeyRepository(
                    redditApi = getRedditApi(),
                    networkExecutor = getNetworkExecutor())
        }
    }

