package com.example.demo.unsplash.core

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@MainThread
fun <T> MutableLiveData<T>.update(transform: (T) -> T) {
  check(this.isInitialized) {
    "Missing initial value."
  }

  @Suppress("UNCHECKED_CAST")
  this.value = transform(value as T)
}


fun <T> LiveData<T>.debounce(duration: Long = 1000L, coroutineScope: CoroutineScope) = MediatorLiveData<T>().also { mld ->

  val source = this
  var job: Job? = null

  mld.addSource(source) {
    job?.cancel()
    job = coroutineScope.launch {
      delay(duration)
      mld.value = source.value
    }
  }
}
