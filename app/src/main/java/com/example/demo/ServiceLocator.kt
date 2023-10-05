package com.example.demo

import com.example.demo.data.TodoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

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