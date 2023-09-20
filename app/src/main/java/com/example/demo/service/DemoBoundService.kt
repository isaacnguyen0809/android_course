package com.example.demo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlin.random.Random

class DemoBoundService: Service() {
  inner class LocalBinder: Binder() {
    fun getDemoBoundService(): DemoBoundService = this@DemoBoundService
  }

  private val localBinder = LocalBinder()

  override fun onBind(intent: Intent?): IBinder {
    log("onBind [1] intent=$intent")
    return localBinder
  }

  // 0
  override fun onUnbind(intent: Intent?): Boolean {
    log("onUnbind [2] intent=$intent")
    return true// true->onRebind
  }

  // 0 -> 1
  override fun onRebind(intent: Intent?) {
    super.onRebind(intent)
    log("onRebind [3] intent=$intent")
  }

  private fun log(message: String) {
    Log.d(TAG, "${hashCode()}: $message")
  }

  fun getDemoData(): Int {
    return Random.nextInt()
  }

  companion object {
    private const val TAG = "DemoBoundService"
  }
}