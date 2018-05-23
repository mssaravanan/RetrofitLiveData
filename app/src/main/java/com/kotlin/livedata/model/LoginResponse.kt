package com.kotlin.livedata.model

data class LoginResponse(var AccessToken: String? = null,
                         var Status: Boolean = false,
                         var Message: String? = null,
                         var Data: TasterUserData? = null)