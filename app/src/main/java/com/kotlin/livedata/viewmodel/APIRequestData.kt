package com.kotlin.livedata.viewmodel

import okhttp3.RequestBody

data class APIRequestData (var header:String="fds",var requestBody: RequestBody)