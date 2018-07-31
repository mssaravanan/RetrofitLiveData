package com.kotlin.livedata.utility

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

public object Utility {


    fun logError(str: String) {
        Log.e("Error", str)
    }


    fun showToast(context: Context, str: String?) {

        Toast.makeText(context, str, Toast.LENGTH_LONG).show()
    }

    public fun isInternetConnectedCheck(context: Context): Boolean {

        val connec = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connec
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val bluetooth = connec
                .getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH)


        if (wifi.isConnected) {
            return true
        } else if (mobile.isConnected) {
            return true
        } else if (bluetooth!=null&&bluetooth.isConnected) {
            return true
        } else if (!mobile.isConnected) {
            return false
        }
        return false

    }


}