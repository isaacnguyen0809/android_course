package com.example.demo.demo_api.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TodoServiceLocator {

  private const val BASE_URL = "https://jsonplaceholder.typicode.com"

  private val retrofitProvider by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  val todoService by lazy {
    TodoService.createTodoService(retrofitProvider)
  }
}
