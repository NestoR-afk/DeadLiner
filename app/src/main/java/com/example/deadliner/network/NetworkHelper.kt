package com.example.deadliner.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkHelper() {
    companion object {
        fun checkConnection(context: Context) : Boolean {

            val cm  = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return ((cm.activeNetworkInfo != null) && (cm.activeNetworkInfo!!.isConnected()))
        }
    }
}