package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvClick.setOnClickListener {
            val data: String = "This is the message from MainActivity"
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("message", data)
            }
//            startActivity(intent)
//            finish()
            resultLauncher.launch(intent)
//            counter++
//            binding.tvCounter.text = counter.toString()
        }
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(KEY_COUNTER, 0)
            binding.tvCounter.text = counter.toString()
        }
        Log.d(TAG, "onCreate: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNTER, counter)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentData = result.data
                val message = intentData?.getStringExtra("result")
//                binding.tvMain.text = message
                Toast.makeText(this, "Result: $message", Toast.LENGTH_SHORT).show()
            }
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
        private const val TAG = "MainActivity"
        private const val KEY_COUNTER = "KEY_COUNTER"
    }
}