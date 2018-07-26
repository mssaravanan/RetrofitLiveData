package com.kotlin.livedata.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WineHistoryResponse(@SerializedName("Status")
                               @Expose
                               var Status: Boolean = false,

                               @SerializedName("Message")
                               @Expose
                               var Message: String? = null,
                               @SerializedName("Data")
                               @Expose
                               var data: Data? = null) {
    data class Data(@SerializedName("Brand")
                    @Expose
                    var brand: String? = null,
                    @SerializedName("WineBrandPhotoPath")
                    @Expose
                    var wineBrandPhotoPath: String? = null,
                    @SerializedName("RatingValue")
                    @Expose
                    var ratingValue: Int? = null,
                    @SerializedName("Blend")
                    @Expose
                    var blend: String? = null,
                    @SerializedName("Varietal")
                    @Expose
                    var varietal: String? = null,
                    @SerializedName("WinemakerBio")
                    @Expose
                    var winemakerBio: String? = null,
                    @SerializedName("WineryName")
                    @Expose
                    var wineryName: String? = null,
                    @SerializedName("Latitude")
                    @Expose
                    var latitude: Float? = null,
                    @SerializedName("Longitude")
                    @Expose
                    var longitude: Float? = null,
                    @SerializedName("WYear")
                    @Expose
                    var wYear: String? = null,
                    @SerializedName("EventDescription")
                    @Expose
                    var eventDescription: String? = null)

}