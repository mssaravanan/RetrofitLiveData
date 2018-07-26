@file:JvmName("WineDataBindingAdapter")
package com.kotlin.livedata.binding
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("resourcesVisibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}


@BindingAdapter(value = ["imageUrl","progressView"],requireAll = true)
fun imageURL(view: ImageView, url: String?,progressBar: ProgressBar) {

    url.isNullOrEmpty().let {
        if(!it){
            Glide.with(view.context).load(url).listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    progressBar.visibility=View.GONE
                    e?.printStackTrace()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progressBar.visibility=View.GONE
                    return false
                }

            }).into(view)
        }
    }

}


