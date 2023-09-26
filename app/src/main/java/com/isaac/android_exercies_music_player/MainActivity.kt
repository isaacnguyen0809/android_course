package com.isaac.android_exercies_music_player

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.isaac.android_exercies_music_player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var networkReceiver: NetworkReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        networkReceiver = NetworkReceiver()
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 0)
        }

        binding.play.setOnClickListener {
            ContextCompat.startForegroundService(this, Intent(applicationContext, MusicService::class.java).apply {
                putExtra("ACTION", "PLAY")
            })
        }

        binding.pause.setOnClickListener {
            ContextCompat.startForegroundService(this, Intent(applicationContext, MusicService::class.java).apply {
                putExtra("ACTION", "PAUSE")
            })
        }

        binding.stop.setOnClickListener {
            stopService(Intent(this, MusicService::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
    }

}