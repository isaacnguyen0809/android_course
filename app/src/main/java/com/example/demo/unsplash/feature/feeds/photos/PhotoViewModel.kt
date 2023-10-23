package com.example.demo.unsplash.feature.feeds.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.unsplash.core.update
import com.example.demo.unsplash.data.remote.UnsplashApiService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class PhotoViewModel constructor(
  private val unsplashApiService: UnsplashApiService
) : ViewModel() {
  private val _uiStateLiveData = MutableLiveData<PhotoUiState>(PhotoUiState.Loading)

  val uiStateLiveData: LiveData<PhotoUiState> get() = _uiStateLiveData

  init {
    getPhotos()
  }

  fun getPhotos() {
    viewModelScope.launch {
      _uiStateLiveData.update { PhotoUiState.Loading }
      try {

        val result = unsplashApiService.getPhotos(
          orderBy = "popular",
          page = 1,
          perPage = 20
        )

        _uiStateLiveData.update {
          PhotoUiState.Success(
            currentPage = 1,
            listPhoto = result.map {
              it.toPhotoUiItem()
            },
            isLoadingNextPage = false,
            errorLoadNextPage = null,
            isRefreshing = false,
          )
        }

      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        _uiStateLiveData.update { PhotoUiState.Failure(e) }
      }
    }
  }

  internal fun loadNextPage() {
    val currentUiState = _uiStateLiveData.value!!
    if (currentUiState !is PhotoUiState.Success) {
      // ignore, not ready to load next page
      return
    }

    // call 1 time only
    if (!currentUiState.isLoadingNextPage && currentUiState.errorLoadNextPage == null) {
      // toggle loading next page
      _uiStateLiveData.update { currentUiState.copy(isLoadingNextPage = true) }

      val nextPage = currentUiState.currentPage + 1

      viewModelScope.launch {
        try {
          val responseItems = unsplashApiService.getPhotos(
            page = nextPage,
            perPage = 20,
            orderBy = "popular"
          )
          _uiStateLiveData.update {
            PhotoUiState.Success(
              currentPage = nextPage,
              listPhoto = (currentUiState.listPhoto + responseItems.map { it.toPhotoUiItem() }).distinctBy { it.id },
              isLoadingNextPage = false,
              errorLoadNextPage = null,
              isRefreshing = false,
            )
          }

        } catch (e: CancellationException) {
          throw e
        } catch (e: Exception) {

          _uiStateLiveData.update {
            currentUiState.copy(
              isLoadingNextPage = false,
              errorLoadNextPage = e
            )
          }
        }
      }
    }
  }

}
