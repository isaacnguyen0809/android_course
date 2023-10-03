package com.example.demo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.demo.databinding.ActivityDemoViewModelBinding
import com.example.demo.viewmodel.DemoViewModel
import com.example.demo.viewmodel.DemoViewModelFactory

class DemoViewModelActivity : AppCompatActivity() {

    private val simpleViewModel by viewModels<DemoViewModel>(
        factoryProducer = {
            viewModelFactory {
                addInitializer(DemoViewModel::class) {
                    DemoViewModel("DataFromActivity", 1)
                }
            }
        }
    )

    private val binding by lazy {
        ActivityDemoViewModelBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvClick.setOnClickListener {
            simpleViewModel.counter()
        }

        showText()
        Log.d(TAG, "onCreate")
    }

    private fun showText() {
        simpleViewModel.badgeCount.observe(this) {
            binding.tvCounter.text = it.toString()
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    companion object {
        private const val TAG = "DemoViewModelActivity"
    }
}