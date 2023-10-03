package com.example.demo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DemoViewModel(param1: String, param2: Int) : ViewModel() {

    init {
        Log.d("DemoViewModelActivity", "init $param1 $param2")
    }

    private var counter = 0

    private val _badgeCount = MutableLiveData<Int>()
    val badgeCount: LiveData<Int>
        get() = _badgeCount


   fun counter() {
       viewModelScope.launch(Dispatchers.IO) {
           _badgeCount.value = ++counter
       }

    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DemoViewModelActivity", "onCleared")
    }
}