package com.example.demo.unsplash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.demo.R
import com.example.demo.databinding.UnsplashMainActivityBinding
import com.example.demo.unsplash.feature.feeds.FeedsFragment
import kotlin.LazyThreadSafetyMode.NONE

class UnsplashMainActivity : AppCompatActivity() {
  private val binding by lazy(NONE) { UnsplashMainActivityBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    if (savedInstanceState == null) {
      supportFragmentManager.commit {
        setReorderingAllowed(true)

        add<FeedsFragment>(
          containerViewId = R.id.fragment_container,
          tag = FeedsFragment::class.java.simpleName,
        )
      }
    }
  }
}
