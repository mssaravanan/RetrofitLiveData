package com.kotlin.livedata.ui.searchwine

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kotlin.livedata.R
import com.kotlin.livedata.api.paging.NetworkState
import com.kotlin.livedata.api.paging.PageViewModel
import com.kotlin.livedata.api.paging.ServiceLocator
import com.kotlin.livedata.constants.DevicePreferences
import com.kotlin.livedata.model.SearchData
import com.kotlin.livedata.model.SearchWine
import com.kotlin.livedata.ui.winedetail.WineDetailFragment
import kotlinx.android.synthetic.main.activity_search_wine.*


class SearchActivity:AppCompatActivity() {

    lateinit var model: PageViewModel<SearchWine.SearchWineData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_wine)
        model = getViewModel()
        initAdapter()
        initSwipeToRefresh()
        initSearch()
        searchBtn.setOnClickListener {
            updatedSearchFromInput()
        }
    }

    //Get the search view model
    private fun getViewModel(): PageViewModel<SearchWine.SearchWineData> {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                val repo = ServiceLocator.instance(this@SearchActivity)
                        .getRepository()
                @Suppress("UNCHECKED_CAST")
                return PageViewModel<SearchWine.SearchWineData>(repo) as T

            }
        })[PageViewModel::class.java] as PageViewModel<SearchWine.SearchWineData>
    }


    //Press search or submit button to search
    private fun initSearch() {
        editText.setOnEditorActionListener(
                { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updatedSearchFromInput()
                true
            } else {
                false
            }
        })
        editText.setOnKeyListener({ v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updatedSearchFromInput()
                true
            } else {
                false
            }
        })
    }

    //Submit the text to Search API Via model
    private fun updatedSearchFromInput() {
        editText.text.trim().toString().let {
            if (it.isNotEmpty()) {
                var searchData=SearchData(it,DevicePreferences.getInt(this@SearchActivity,"userId"))
                if (model.showSearchData(searchData)) {
                    searchRV.scrollToPosition(0)
                    (searchRV.adapter as? SearchWineAdapter)?.submitList(null)
                }
            }
        }
    }


    fun onItemClick(post:SearchWine.SearchWineData?){
        Toast.makeText(this@SearchActivity,"xfsdfsdsd",Toast.LENGTH_LONG).show()
        var wineDetailFragment=WineDetailFragment()
        var bundle=Bundle()
        bundle.putInt("WineId",post?.wineId?:0)
        bundle.putString("RequestFrom","Wine")
        bundle.putInt("UserId", DevicePreferences.getInt(this@SearchActivity,"userId"))
        wineDetailFragment.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder,wineDetailFragment,"wineDetail")
                .commit()
    }


    //Recycler view Adapter
    private fun initAdapter() {
        val glide = Glide.with(this)
        val adapter = SearchWineAdapter(glide){
            model.retry() }

        searchRV.adapter = adapter


        model.posts.observe(this, Observer<PagedList<SearchWine.SearchWineData>> {
            adapter.submitList(it)
        })

        model.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })


        model.dataState.observe(this, Observer {


        })
    }


    //Swipe to Refresh
    private fun initSwipeToRefresh() {
        model.refreshState.observe(this, Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })
        swipe_refresh.setOnRefreshListener {
            model.refresh()
        }
    }

    override fun onBackPressed() {
       // var fragment = supportFragmentManager.findFragmentByTag("wineDetail")
        if(supportFragmentManager.fragments.size>0){
            var fragment = supportFragmentManager.fragments.get(0)
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }else{
            super.onBackPressed()
        }

    }
}


