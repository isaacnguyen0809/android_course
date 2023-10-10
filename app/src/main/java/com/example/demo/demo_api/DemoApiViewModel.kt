package com.example.demo.demo_api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.demo_api.data.TodoRequest
import com.example.demo.demo_api.data.TodoResponse
import com.example.demo.demo_api.data.TodoService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DemoApiViewModel(private val todoService: TodoService) : ViewModel() {

  private val _todoLiveData = MutableLiveData<TodoStateApi>()
  val todoLivedata: LiveData<TodoStateApi> get() = _todoLiveData

  fun getTodo() {
    _todoLiveData.value = TodoStateApi.Loading

    todoService.getTodo().enqueue(object : Callback<TodoResponse> {
      override fun onResponse(call: Call<TodoResponse>, response: Response<TodoResponse>) {
        if (response.isSuccessful && response.body() != null) {
          _todoLiveData.value = TodoStateApi.Success(response.body()!!)
        } else {
          _todoLiveData.value = TodoStateApi.Failed(Exception("Error"))
        }
      }

      override fun onFailure(call: Call<TodoResponse>, t: Throwable) {

      }

    })
  }

  fun getTodoSuspend() {
    viewModelScope.launch {
      _todoLiveData.value = TodoStateApi.Loading
      try {
        val result = todoService.getTodoSuspend()
        _todoLiveData.value = TodoStateApi.Success(result)
      } catch (e: Exception) {
        _todoLiveData.value = TodoStateApi.Failed(Exception("Error"))
      }
    }
  }

  fun todoPostApi(todoRequest: TodoRequest) {
    viewModelScope.launch {
      _todoLiveData.value = TodoStateApi.Loading
      try {
        val result = todoService.postTodoApi(todoRequest)
        _todoLiveData.value = TodoStateApi.PostSuccess(result)
      } catch (e: Exception) {
        _todoLiveData.value = TodoStateApi.Failed(Exception("Error"))
      }
    }
  }

  fun todoGetApi() {
    viewModelScope.launch {
      _todoLiveData.value = TodoStateApi.Loading
      try {
        val result = todoService.getTodoList()
        _todoLiveData.value = TodoStateApi.GetListSuccess(result)
      } catch (e: Exception) {
        _todoLiveData.value = TodoStateApi.Failed(Exception("Error"))
      }
    }
  }
}
