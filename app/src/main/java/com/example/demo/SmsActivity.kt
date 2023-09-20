package com.example.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivitySmsReceiverBinding

class SmsActivity : AppCompatActivity() {

  private val binding by lazy { ActivitySmsReceiverBinding.inflate(layoutInflater) }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    val numberPhone = intent.getStringExtra(SmsBroadCastReceiver.EXTRA_SMS_NO)
    val bodyMessage = intent.getStringExtra(SmsBroadCastReceiver.EXTRA_SMS_BODY)

    binding.tvPhoneNumber.text = numberPhone
    binding.tvContent.text = bodyMessage
  }


}