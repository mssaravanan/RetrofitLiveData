package com.kotlin.livedata.ui.searchwine

import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.kotlin.livedata.R
import com.kotlin.livedata.api.paging.NetworkState
import com.kotlin.livedata.api.paging.NetworkStateItemViewHolder
import com.kotlin.livedata.model.SearchWine


class SearchWineAdapter(private val glide: RequestManager, val retryCallback: () -> Unit) : PagedListAdapter<SearchWine.SearchWineData,RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private var networkState: NetworkState? = null


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.row_search_wine_item -> (holder as WinePostViewHolder).bind(getItem(position))
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(
                    networkState)
        }
    }


    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int,
            payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            (holder as WinePostViewHolder).updateScore(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.row_search_wine_item -> WinePostViewHolder.create(parent,glide)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.row_search_wine_item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }


    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<SearchWine.SearchWineData>() {
            override fun areContentsTheSame(oldItem: SearchWine.SearchWineData, newItem: SearchWine.SearchWineData): Boolean =
                    oldItem.wineId == newItem.wineId

            override fun areItemsTheSame(oldItem: SearchWine.SearchWineData, newItem: SearchWine.SearchWineData): Boolean =
                    oldItem.wineId == newItem.wineId

        }





    }
}