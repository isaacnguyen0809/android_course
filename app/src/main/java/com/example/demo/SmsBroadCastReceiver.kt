package com.example.demo

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony

class SmsBroadCastReceiver : BroadcastReceiver() {
  @SuppressLint("UnsafeProtectedBroadcastReceiver")
  override fun onReceive(context: Context?, intent: Intent?) {
    val bundle = intent?.extras

    try {
      bundle?.let {
        val extraMessage = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extraMessage.forEach { currentMessage ->
          val smsIntent = Intent(context, SmsActivity::class.java).apply {
            putExtra(EXTRA_SMS_NO, currentMessage.displayOriginatingAddress)
            putExtra(EXTRA_SMS_BODY, currentMessage.displayMessageBody)
          }
          context?.startActivity(smsIntent)
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  companion object {
    private val TAG: String = "SmsBroadCastReceiver"
    const val EXTRA_SMS_NO = "EXTRA_SMS_NO"
    const val EXTRA_SMS_BODY = "EXTRA_SMS_BODY"
  }

}