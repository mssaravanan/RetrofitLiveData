package com.kotlin.livedata.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

public data class NotesResponse(@SerializedName("Status")
                         @Expose
                         var Status: Boolean? = false,

                         @SerializedName("Message")
                         @Expose
                         var Message: String? = null,
                         @SerializedName("Data")
                         @Expose
                         var data: ArrayList<NotesData>? = null) {
    public  data class NotesData(@SerializedName("WineNoteId")
                         @Expose
                         var wineNoteId: Int? = null,
                         @SerializedName("WineryId")
                         @Expose
                         var wineryId: Int? = null,
                         @SerializedName("WineId")
                         @Expose
                         var wineId: Int? = null,
                         @SerializedName("UserId")
                         @Expose
                         var userId: Int? = null,

                         @SerializedName("Note")
                         @Expose
                         var note: String? = null,

                         @SerializedName("NotesAddedOn")
                         @Expose
                         var notesAddedOn: String? = null)

}