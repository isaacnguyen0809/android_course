package com.example.demo.unsplash.core

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData

@MainThread
fun <T> MutableLiveData<T>.update(transform: (T) -> T) {
  check(this.isInitialized) {
    "Missing initial value."
  }

  @Suppress("UNCHECKED_CAST")
  this.value = transform(value as T)
}
