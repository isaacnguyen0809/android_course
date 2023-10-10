package com.example.demo.unsplash.feature.feeds.collections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.unsplash.core.update
import com.example.demo.unsplash.data.remote.UnsplashApiService
import com.example.demo.unsplash.data.remote.response.CollectionItemResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class CollectionsViewModel constructor(
  private val unsplashApiService: UnsplashApiService,
) : ViewModel() {
  private val _uiStateLiveData =
    MutableLiveData<CollectionsUiState>(CollectionsUiState.FirstPageLoading)

  internal val uiStateLiveData: LiveData<CollectionsUiState> get() = _uiStateLiveData

  init {
    loadFirstPage()
  }

  private fun loadFirstPage() {
    viewModelScope.launch {
      _uiStateLiveData.update { CollectionsUiState.FirstPageLoading }

      try {
        val responseItems = unsplashApiService.getCollections(
          page = 1,
          perPage = PER_PAGE,
        )

        _uiStateLiveData.update {
          CollectionsUiState.Content(
            currentPage = 1,
            items = responseItems.map { it.toCollectionUiItem() },
            isLoadingNextPage = false,
            errorNextPage = null,
            isRefreshing = false,
          )
        }

        Log.d(LOG_TAG, "loadFirstPage: ${responseItems.size}")
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.e(LOG_TAG, "loadFirstPage: $e", e)

        _uiStateLiveData.update { CollectionsUiState.FirstPageFailure(e) }
      }
    }
  }

  // Load more...
  internal fun loadNextPage() {
    val currentUiState = _uiStateLiveData.value!!
    if (currentUiState !is CollectionsUiState.Content) {
      // ignore, not ready to load next page
      return
    }

    // call 1 time only
    if (!currentUiState.isLoadingNextPage && currentUiState.errorNextPage == null) {
      // toggle loading next page
      _uiStateLiveData.update { currentUiState.copy(isLoadingNextPage = true) }

      val nextPage = currentUiState.currentPage + 1

      viewModelScope.launch {
        try {
          val responseItems = unsplashApiService.getCollections(
            page = nextPage,
            perPage = PER_PAGE,
          )

          _uiStateLiveData.update {
            CollectionsUiState.Content(
              currentPage = nextPage,
              items = (currentUiState.items + responseItems.map { it.toCollectionUiItem() })
                .distinctBy { it.id },
              isLoadingNextPage = false,
              errorNextPage = null,
              isRefreshing = false,
            )
          }

          Log.d(LOG_TAG, "loadNextPage: ${responseItems.size}")
        } catch (e: CancellationException) {
          throw e
        } catch (e: Exception) {
          Log.e(LOG_TAG, "loadNextPage: $e", e)

          _uiStateLiveData.update {
            currentUiState.copy(
              isLoadingNextPage = false,
              errorNextPage = e
            )
          }
        }
      }
    }
  }

  fun refresh() {
    val currentUiState = _uiStateLiveData.value!!
    if (currentUiState !is CollectionsUiState.Content) {
      // ignore, not ready to refresh
      return
    }

    if (currentUiState.isRefreshing) {
      // ignore, already refreshing
      return
    }

    _uiStateLiveData.update {
      currentUiState.copy(isRefreshing = true)
    }

    viewModelScope.launch {
      try {
        val responseItems = unsplashApiService.getCollections(
          page = 1,
          perPage = PER_PAGE,
        )

        _uiStateLiveData.update {
          // reset to page 1
          CollectionsUiState.Content(
            isRefreshing = false,
            items = responseItems.map { it.toCollectionUiItem() },
            currentPage = 1,
            isLoadingNextPage = false,
            errorNextPage = null
          )
        }

        Log.d(LOG_TAG, "refresh: ${responseItems.size}")
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.e(LOG_TAG, "refresh: $e", e)

        _uiStateLiveData.update {
          // toggle isRefreshing
          currentUiState.copy(
            isRefreshing = false
          )
        }
      }
    }
  }

  companion object {
    private const val PER_PAGE = 30
    private const val LOG_TAG = "CollectionsViewModel"
  }
}

private fun CollectionItemResponse.toCollectionUiItem(): CollectionUIItem = CollectionUIItem(
  id = id,
  title = title,
  description = description.orEmpty(),
  coverPhotoUrl = coverPhoto.urls.regular,
)
