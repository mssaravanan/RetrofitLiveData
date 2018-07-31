package com.kotlin.livedata.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddNoteResponse(@SerializedName("Status")
                           @Expose
                           var Status: Boolean = false,

                           @SerializedName("Message")
                           @Expose
                           var Message: String? = null,
                           @SerializedName("Data")
                           @Expose
                           var data: Any? = null)