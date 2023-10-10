package com.example.demo.demo_api

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.demo.data.TodoRequest
import com.example.demo.data.TodoServiceLocator
import com.example.demo.databinding.ActivityDemoApiBinding


class DemoApiActivity : AppCompatActivity() {

  private val binding by lazy {
    ActivityDemoApiBinding.inflate(layoutInflater)
  }

  private val viewModel by viewModels<DemoApiViewModel>(
    factoryProducer = {
      viewModelFactory {
        addInitializer(DemoApiViewModel::class) {
          DemoApiViewModel(TodoServiceLocator.todoService)
        }
      }
    }
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

//        val policy = ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)

    binding.btnRequest.setOnClickListener {
      val todoRequest = TodoRequest(
        title = "Hello Android 003",
        body = "Android",
        userId = 1
      )
      viewModel.todoGetApi()
    }
    observeTodo()
  }

  private fun observeTodo() {
    viewModel.todoLivedata.observe(this) {
      when (it) {
        is TodoStateApi.Loading -> {
          binding.progressBar.visibility = View.VISIBLE
        }

        is TodoStateApi.Success -> {
          binding.progressBar.visibility = View.GONE
          binding.tvCounter.text = it.todoResponse.toString()
        }

        is TodoStateApi.Failed -> {
          binding.progressBar.visibility = View.GONE
        }

        is TodoStateApi.PostSuccess -> {
          binding.progressBar.visibility = View.GONE
          binding.tvCounter.text = it.todoResponse.toString()
        }

        is TodoStateApi.GetListSuccess -> {
          binding.progressBar.visibility = View.GONE
          binding.tvCounter.text = it.todoResponse.joinToString { todoPostResponse ->
            todoPostResponse.toString()
          }
        }
      }
    }
  }
}
