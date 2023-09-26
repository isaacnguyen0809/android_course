package com.isaac.android_exercies_music_player

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class NetworkReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        if (networkInfo?.isConnected == true) {
            var stringType = ""
            stringType = when (networkInfo?.type) {
                ConnectivityManager.TYPE_WIFI -> "wifi"
                ConnectivityManager.TYPE_MOBILE -> "hostpot"
                else -> "Unspecified"
            }
            Toast.makeText(context, "${stringType} connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        }

    }

}