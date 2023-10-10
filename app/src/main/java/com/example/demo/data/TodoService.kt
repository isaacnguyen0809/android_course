package com.example.demo.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoService {

  @GET("/todos/1")
  fun getTodo(): Call<TodoResponse>

  @GET("/todos/1")
  suspend fun getTodoSuspend(): TodoResponse

  @POST("/posts")
  suspend fun postTodoApi(@Body todoRequest: TodoRequest): TodoPostResponse

  @GET("/posts")
  suspend fun getTodoList(): List<TodoPostResponse>

  companion object {
    fun createTodoService(retrofit: Retrofit): TodoService {
      return retrofit.create(TodoService::class.java)
    }
  }
}
