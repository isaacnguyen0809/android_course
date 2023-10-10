package com.example.demo.unsplash.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.demo.unsplash.core.debounce
import com.example.demo.unsplash.data.remote.UnsplashApiService
import com.example.demo.unsplash.feature.feeds.collections.CollectionUIItem

class SearchViewModel(private val unsplashApiService: UnsplashApiService) : ViewModel() {

  private val _querySearchLiveData = MutableLiveData("")
  private val querySearchLiveData: LiveData<String> get() = _querySearchLiveData

  val searchPhotoResult = querySearchLiveData
    .debounce(duration = 600, coroutineScope = viewModelScope)
    .switchMap { query ->
      liveData {
        val result = unsplashApiService.searchPhotos(query, 1, 10).results.map {
          CollectionUIItem(
            id = it.id,
            title = it.description ?: "",
            description = it.description ?: "",
            coverPhotoUrl = it.urls.regular
          )
        }
        emit(result)
      }
    }

  fun searchPhotos(query: String) {
    _querySearchLiveData.value = query
  }
}
