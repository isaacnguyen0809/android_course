package com.example.demo.fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener
import com.example.demo.databinding.ActivityDemoFragmentsBinding
import kotlin.LazyThreadSafetyMode.NONE

class DemoFragmentsActivity : AppCompatActivity() {
  private val binding by lazy(NONE) { ActivityDemoFragmentsBinding.inflate(layoutInflater) }

  private val onBackStackChangedListener = OnBackStackChangedListener {
    val backStackEntryCount = supportFragmentManager.backStackEntryCount

    Log.d("DemoFragmentsActivity", "onBackStackChanged: backStackEntryCount=$backStackEntryCount")

    val message = (0..<backStackEntryCount)
      .map { supportFragmentManager.getBackStackEntryAt(it) }
      .joinToString(",\n")

    Log.d("DemoFragmentsActivity", "onBackStackChanged: $message")

    binding.textView.text = message
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    supportFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)
  }

  override fun onDestroy() {
    supportFragmentManager.removeOnBackStackChangedListener(onBackStackChangedListener)
    super.onDestroy()
  }
}