package com.example.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DemoViewModelFactory(private val param1: String, private val param2: Int) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DemoViewModel::class.java)) {
            return modelClass.getConstructor(String::class.java, Int::class.java)
                .newInstance(param1, param2)
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}