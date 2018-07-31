package com.kotlin.livedata.ui.winenote

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kotlin.livedata.R
import com.kotlin.livedata.constants.DevicePreferences
import com.kotlin.livedata.databinding.ActivityAddNoteBinding
import com.kotlin.livedata.model.AddNoteData
import com.kotlin.livedata.model.AddNoteResponse
import com.kotlin.livedata.model.NotesResponse
import com.kotlin.livedata.viewmodel.APIRequestData
import com.kotlin.livedata.viewmodel.MultiAPIViewModel
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.ArrayList

class AddNoteActivity : AppCompatActivity() {

    lateinit var addNoteBinding:ActivityAddNoteBinding

    var listA= listOf<Int>(1,2,3,4)
    var listB = listOf<Int>(1,2,3,4)

    private val apiViewModel: MultiAPIViewModel by lazy {
        ViewModelProviders.of(this).get(MultiAPIViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addNoteBinding=DataBindingUtil.setContentView(this,R.layout.activity_add_note)
        addNoteBinding.addnote=this@AddNoteActivity
        addNoteBinding.addnotedata=AddNoteData()
        print(listA.containsAll(listB))
        apiViewModel.getSingleResponse<AddNoteResponse>().observe(this, Observer {

        })
    }



    fun onSubmitNote(view: View, addNoteData: AddNoteData ){


    }


    fun onSubmitNoteString(view: View, addNoteData: String ){
        var apiDataList = ArrayList<APIRequestData>()
        try {
            var jsonObject = JSONObject()

            jsonObject.put("WineId", intent?.extras?.get("WineId"))

            jsonObject.put("UserId",  intent?.extras?.get("UserId"))
            jsonObject.put("WineNote",addNoteData)
            apiDataList.add(APIRequestData(DevicePreferences.getString(this@AddNoteActivity,"userToken")!!, RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString())))
            apiViewModel.setRequestBody(apiDataList)

        }catch (e:Exception){

        }
    }

}
