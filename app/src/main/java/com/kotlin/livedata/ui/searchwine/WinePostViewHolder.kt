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

package com.kotlin.livedata.ui.searchwine

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kotlin.livedata.R
import com.kotlin.livedata.model.SearchWine

/**
 * A RecyclerView ViewHolder that displays a reddit post.
 */
class WinePostViewHolder(view: View,private val glide: RequestManager,context: SearchActivity?)
    : RecyclerView.ViewHolder(view) {


   /* constructor(view: View,glide: RequestManager,str:String) : this(view,glide,context = null) {

    }*/

    private val title: TextView = view.findViewById(R.id.txt_wine_name)
    private val subtitle: TextView = view.findViewById(R.id.txt_winery_name)
    private val score: TextView = view.findViewById(R.id.txt_winery_address)
    private val img_wine:ImageView=view.findViewById(R.id.img_wine);
    private val progressBarSearchWine:ProgressBar=view.findViewById(R.id.progressBarSearchWine);
    private var post : SearchWine.SearchWineData? = null
    init {
        view.setOnClickListener {
            context?.onItemClick(post)
        }
    }

    fun bind(post: SearchWine.SearchWineData?) {
        this.post = post
        title.text = post?.blendItemName ?: "loading"
        subtitle.text =post?.companyName ?: "unknown"
        score.text = post?.wineryAddress ?: "unknown"

        post?.wineBrandPhotoPath.isNullOrEmpty().let {
            if(!it){
                glide.load(post?.wineBrandPhotoPath).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBarSearchWine.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBarSearchWine.visibility=View.GONE
                        return false
                    }


                }).into(img_wine)
            }else{
                progressBarSearchWine.visibility=View.GONE
            }

        }


    }

    companion object {
        fun create(parent: ViewGroup,glide:RequestManager): WinePostViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_search_wine_item, parent, false)
            return WinePostViewHolder(view,glide,parent.context as SearchActivity)
        }
    }

    fun updateScore(item: SearchWine.SearchWineData?) {
        post = item
    }
}