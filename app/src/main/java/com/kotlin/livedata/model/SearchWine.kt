package com.kotlin.livedata.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class SearchWine(
        @SerializedName("Data")
        @Expose
        var dataList:ArrayList<SearchWineData> ,
        @SerializedName("Status")
        @Expose
        var Status: Boolean = false,

        @SerializedName("Message")
        @Expose
        var Message: String? = null ){
        data class SearchWineData(
                @SerializedName("WineId")
                @Expose
                var wineId: Int? = null,

                @SerializedName("BrandName")
                @Expose
                var brandName: String? = null,
                @SerializedName("WineBrandPhotoPath")
                @Expose
                var wineBrandPhotoPath: String? = null,
                @SerializedName("RatingValue")
                @Expose
                var ratingValue: Int? = null,
                @SerializedName("WineMakerNotes")
                @Expose
                var wineMakerNotes: String? = null,
                @SerializedName("WineryId")
                @Expose
                var wineryId: Int? = null,
                @SerializedName("CompanyName")
                @Expose
                var companyName: String? = null,
                @SerializedName("CompanyAddress")
                @Expose
                var companyAddress: String? = null,
                @SerializedName("Latitude")
                @Expose
                var latitude: Int? = null,

                @SerializedName("Longitude")
                @Expose
                var longitude: Int? = null,

                @SerializedName("Year")
                @Expose
                var year: String? = null,
                @SerializedName("VarietalItemId")
                @Expose
                var varietalItemId: Int? = null,
                @SerializedName("VarietalItemName")
                @Expose
                var varietalItemName: String? = null,
                @SerializedName("BlendItemId")
                @Expose
                var blendItemId: Int? = null,
                @SerializedName("BlendItemName")
                @Expose
                var blendItemName: String? = null,

                @SerializedName("WineryAddress")
                @Expose
                var wineryAddress: String? = null,

                @SerializedName("City")
                @Expose
                var city: String? = null,

                @SerializedName("State")
                @Expose
                var state: String? = null,

                @SerializedName("ZipCode")
                @Expose
                var zipCode: String? = null)
}
