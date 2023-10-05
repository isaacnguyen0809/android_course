package com.example.demo.demo_api

import com.example.demo.data.TodoPostResponse
import com.example.demo.data.TodoResponse

sealed class TodoStateApi {
    data object Loading : TodoStateApi()

    data class Success(val todoResponse: TodoResponse) : TodoStateApi()
    data class PostSuccess(val todoResponse: TodoPostResponse) : TodoStateApi()
    data class GetListSuccess(val todoResponse: List<TodoPostResponse>) : TodoStateApi()

    data class Failed(val error: Exception) : TodoStateApi()
}