package com.example.demo

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.demo.databinding.ActivityDemoViewModelBinding
import com.example.demo.localfile.LocalFileViewModel
import com.example.demo.viewmodel.DemoViewModel
import android.Manifest
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

    private val viewModelLocalFile by viewModels<LocalFileViewModel>()

    private val binding by lazy {
        ActivityDemoViewModelBinding.inflate(layoutInflater)
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Toast.makeText(this, "Da co quyen", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Chua co quyen", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvClick.setOnClickListener {
//            simpleViewModel.counter()
            requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        binding.tvClickSaveImage.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(R.drawable.logo)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        viewModelLocalFile.saveImageExternal(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
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