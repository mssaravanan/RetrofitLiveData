package com.kotlin.livedata.ui.winenote

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kotlin.livedata.R
import com.kotlin.livedata.databinding.ActivityAddNoteBinding
import com.kotlin.livedata.model.AddNoteData

class AddNoteActivity : AppCompatActivity() {

    lateinit var addNoteBinding:ActivityAddNoteBinding

    var listA= listOf<Int>(1,2,3,4)
    var listB = listOf<Int>(1,2,3,4)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addNoteBinding=DataBindingUtil.setContentView(this,R.layout.activity_add_note)
        addNoteBinding.addnote=this@AddNoteActivity
        addNoteBinding.addnotedata=AddNoteData()
        print(listA.containsAll(listB))

    }



    fun onSubmitNote(view: View, addNoteData: AddNoteData ){


    }


    fun onSubmitNoteString(view: View, addNoteData: String ){
    }

}
