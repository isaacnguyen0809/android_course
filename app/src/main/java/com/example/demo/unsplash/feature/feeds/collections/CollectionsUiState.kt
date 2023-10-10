package com.example.demo.unsplash.feature.feeds.collections

sealed interface CollectionsUiState {
  data object FirstPageLoading : CollectionsUiState

  data class FirstPageFailure(val error: Throwable) : CollectionsUiState

  // Page 1 + Page 2 + ...
  data class Content(
    val items: List<CollectionUIItem>,
    val currentPage: Int,
    val isRefreshing: Boolean,
    val isLoadingNextPage: Boolean,
    val errorNextPage: Throwable?
  ) : CollectionsUiState
}

data class CollectionUIItem(
  val id: String,
  val title: String,
  val description: String,
  val coverPhotoUrl: String,
)
