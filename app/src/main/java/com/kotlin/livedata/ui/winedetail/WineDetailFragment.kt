package com.kotlin.livedata.ui.winedetail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kotlin.livedata.R
import com.kotlin.livedata.constants.DevicePreferences
import com.kotlin.livedata.databinding.FragmentWineDetailBinding
import com.kotlin.livedata.model.NotesResponse
import com.kotlin.livedata.model.WineHistoryResponse
import com.kotlin.livedata.ui.winenote.AddNoteActivity
import com.kotlin.livedata.util.RetryCallback
import com.kotlin.livedata.viewmodel.APIRequestData
import com.kotlin.livedata.viewmodel.MultiAPIViewModel
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class WineDetailFragment : Fragment() {


    lateinit var fragmentWineDetailBinding: FragmentWineDetailBinding
    var jsonObject = JSONObject()
    var apiDataList = ArrayList<APIRequestData>()

    private val apiViewModel: MultiAPIViewModel by lazy {
        ViewModelProviders.of(this).get(MultiAPIViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentWineDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wine_detail, container, false)

        // Inflate the layout for this fragment
        try {

            jsonObject.put("WineId", arguments?.get("WineId"))
            jsonObject.put("RatingBy", arguments?.get("UserId"))
            jsonObject.put("RequestMode", "Inside")
            jsonObject.put("RequestFrom", arguments?.get("RequestFrom"))
            jsonObject.put("TasterRatingId", 0)
            apiDataList.add(APIRequestData(DevicePreferences.getString(activity?.applicationContext!!, "userToken")!!, RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString())))


            jsonObject = JSONObject()
            jsonObject.put("WineId", arguments?.get("WineId"))
            jsonObject.put("userId", arguments?.get("UserId"))
            jsonObject.put("userTimeZone", TimeZone.getDefault().getDisplayName(false, TimeZone.LONG))
            jsonObject.put("userTimeZoneIANA", TimeZone.getDefault().getID())
            apiDataList.add(APIRequestData(DevicePreferences.getString(activity?.applicationContext!!, "userToken")!!, RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString())))

            apiViewModel.setRequestBody(apiDataList)


        } catch (e: Exception) {

        }

       /* apiViewModel.getTwiceResponse<WineHistoryResponse, NotesResponse>().observe(this, Observer {

            fragmentWineDetailBinding.resource = it?.first
            fragmentWineDetailBinding.wineHistoryData = it?.first?.data?.data
            fragmentWineDetailBinding.wineDetailFragment = this@WineDetailFragment

            fragmentWineDetailBinding.callback = object : RetryCallback {
                override fun retry() {
                    apiViewModel.retry()
                }
            }


            Log.d("Notes", it?.second?.message ?: "empty")
            if (it?.second?.data?.data != null && it.second.data?.Status!!) {
                var notesListAdapter = NotesListAdapter(it?.second?.data?.data!!)
                val mLayoutManager = LinearLayoutManager(activity)
                fragmentWineDetailBinding.recyclerView.layoutManager = mLayoutManager
                fragmentWineDetailBinding.recyclerView.adapter = notesListAdapter
                Log.d("NotesResult", it?.second?.data?.Message ?: "emptyData")
            }

        })*/
        return fragmentWineDetailBinding.root
    }

    fun addNote(view: View) {
        var intent = Intent(activity, AddNoteActivity::class.java).apply {
            putExtra("WineId", arguments?.get("WineId") as Int)
            putExtra("UserId", arguments?.get("UserId") as Int)
        }
        startActivity(intent)
    }


}
