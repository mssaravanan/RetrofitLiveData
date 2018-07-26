package com.kotlin.livedata.ui.winedetail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kotlin.livedata.R
import com.kotlin.livedata.constants.DevicePreferences
import com.kotlin.livedata.databinding.FragmentWineDetailBinding
import com.kotlin.livedata.model.WineHistoryResponse
import com.kotlin.livedata.ui.login.LoginViewModel
import com.kotlin.livedata.util.RetryCallBack
import com.kotlin.livedata.viewmodel.APIRequestData
import com.kotlin.livedata.viewmodel.APIViewModel
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 *
 */
class WineDetailFragment : Fragment() {


    lateinit var fragmentWineDetailBinding:FragmentWineDetailBinding


    private val apiViewModel: APIViewModel by lazy {
        ViewModelProviders.of(this).get(APIViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            var jsonObject =JSONObject()
            jsonObject.put("WineId", arguments?.get("WineId"))
            jsonObject.put("RatingBy", arguments?.get("UserId"))
            jsonObject.put("RequestMode", "Inside")
            jsonObject.put("RequestFrom", arguments?.get("RequestFrom"))
            jsonObject.put("TasterRatingId", 0)

            var requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString())
            var apiRequestData= APIRequestData(DevicePreferences.getString(activity?.applicationContext!!,"userToken")!!,requestBody)
            apiViewModel.setRequestBody(apiRequestData)
        }catch (e:Exception){

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentWineDetailBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_wine_detail, container, false)
        // Inflate the layout for this fragment
        apiViewModel.getResponse<WineHistoryResponse>().observe(this, Observer {
            fragmentWineDetailBinding.resource=it
            fragmentWineDetailBinding.wineHistoryData=it?.data?.data
            fragmentWineDetailBinding.retryCallback= object : RetryCallBack {
                override fun retry() {
                    apiViewModel.retry()
                }
            }
        })

        return fragmentWineDetailBinding.root
    }


}
