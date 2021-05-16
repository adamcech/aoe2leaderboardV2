package com.example.aoe2deleaderboardv2.activities

import android.content.Context
import android.net.ConnectivityManager

interface OnlineActivity {

    fun isOnline(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected ?: false
    }

}