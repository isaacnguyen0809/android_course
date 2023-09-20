package com.example.demo.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.demo.R

class MyForegroundService : Service() {
  private var mediaPlayer: MediaPlayer? = null

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    log("onStartCommand intent=$intent")

    createNotificationChannel()

    val notification = NotificationCompat.Builder(
      this,
      getString(R.string.notification_channel_id)
    )
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentTitle("Service $this is running...")
      .setContentText("Good")
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .build()

    // quan trong
    startForeground(
      NOTIFICATION_ID,
      notification
    )

    log("Is main thread: ${Looper.getMainLooper() === Looper.myLooper()}")


    when (intent?.getStringExtra(ACTION_EXTRA_KEY)) {
      "START" -> {
        log("ACTION_EXTRA_KEY START...")

        mediaPlayer
          ?.let {
            if (!it.isPlaying) {
              it.seekTo(0)
              it.start()
            }
          }
          ?: run {
            mediaPlayer = MediaPlayer.create(this, R.raw.sample_sound).apply {
              isLooping = true
              setAudioAttributes(
                AudioAttributes.Builder()
                  .setUsage(AudioAttributes.USAGE_MEDIA)
                  .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                  .build()
              )

              start()
            }
          }
      }

      "RESUME" -> {

      }

      "PAUSE" -> {

      }

      "STOP" -> {

      }

      else -> {

      }
    }

    return START_STICKY
  }

  override fun onCreate() {
    super.onCreate()
    log("onCreate")
  }

  override fun onDestroy() {
    mediaPlayer?.stop()
    mediaPlayer?.release()
    mediaPlayer = null

    log("onDestroy")
    super.onDestroy()
  }

  private fun log(message: String) {
    Log.d(TAG, "${hashCode()}: $message")
  }

  companion object {
    private const val TAG = "MyForegroundService"

    private const val NOTIFICATION_ID = 123

    const val ACTION_EXTRA_KEY = "action"
  }

}

private fun Context.createNotificationChannel() {
  // Create the NotificationChannel, but only on API 26+ because
  // the NotificationChannel class is new and not in the support library
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val name = getString(R.string.channel_name)
    val descriptionText = getString(R.string.channel_description)
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(
      getString(R.string.notification_channel_id),
      name,
      importance
    ).apply {
      description = descriptionText
    }
    // Register the channel with the system
    val notificationManager: NotificationManager =
      getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
  }
}