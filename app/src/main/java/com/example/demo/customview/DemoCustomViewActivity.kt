package com.example.demo.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityDemoCustomViewBinding
import kotlin.LazyThreadSafetyMode.NONE

class DemoCustomViewActivity : AppCompatActivity() {
  private val binding by lazy(NONE) {
    ActivityDemoCustomViewBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    binding.demo.setOnClickListener {

    }
  }
}