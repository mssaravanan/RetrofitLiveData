package com.kotlin.livedata.ui.winedetail

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kotlin.livedata.databinding.RowNotesListBinding
import com.kotlin.livedata.model.NotesResponse

class NotesListAdapter(var notesList:ArrayList<NotesResponse.NotesData>): RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val notesBinding = RowNotesListBinding.inflate(inflater)
        return ViewHolder(notesBinding)
    }

    override fun getItemCount(): Int {
      return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notesList.get(position))
       /* with(notesList.get(position)){
            holder.binding?.run {
                notesResponse = this@with
           executePendingBindings()
             }
        }*/

    }


    inner class ViewHolder(var binding: RowNotesListBinding?): RecyclerView.ViewHolder(binding?.root) {

        fun bind(item: NotesResponse.NotesData) {
            binding?.notesResponse = item
            binding?.executePendingBindings()
        }
    }



}