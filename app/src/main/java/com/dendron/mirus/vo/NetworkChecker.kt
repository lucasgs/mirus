package com.dendron.mirus.vo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkChecker(private val context: Context) {
    fun isNetworkAvailable():Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}

