
@file:JvmName("WineDataBindingAdapter")
package com.kotlin.livedata.binding
import android.databinding.BindingAdapter
import android.view.View

@BindingAdapter("resourcesVisibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}


