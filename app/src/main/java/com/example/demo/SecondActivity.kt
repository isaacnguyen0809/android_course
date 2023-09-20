package com.example.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

  private val binding by lazy(LazyThreadSafetyMode.NONE) {
    ActivitySecondBinding.inflate(
      layoutInflater
    )
  }
  private var counter: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    val mess = intent.getStringExtra("message")
    val data: String = "This is the message from SecondActivity"
    binding.tvSecond.setOnClickListener {
      //            val resultIntent = Intent().apply {
      //                putExtra("result", data)
      //            }
      //            setResult(RESULT_OK, resultIntent)
      //            finish()
      counter++
      binding.tvSecond.text = counter.toString()
    }
    Log.d(TAG, "onCreate: $mess")

    finish()
  }

  override fun onStart() {
    super.onStart()
    Log.d(TAG, "onStart: ")
  }

  override fun onRestart() {
    super.onRestart()
    Log.d(TAG, "onRestart: ")
  }

  override fun onResume() {
    super.onResume()
    Log.d(TAG, "onResume: ")
  }

  override fun onPause() {
    super.onPause()
    Log.d(TAG, "onPause: ")
  }

  override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop: ")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy: ")
  }

  companion object {
    private const val TAG = "SecondActivity"
  }

}