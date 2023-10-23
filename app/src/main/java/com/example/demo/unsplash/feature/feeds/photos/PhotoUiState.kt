package com.example.demo.unsplash.feature.feeds.photos

import com.example.demo.unsplash.data.remote.response.PhotosResponseItem

sealed interface PhotoUiState {
  data object Loading : PhotoUiState
  data class Failure(val e: Throwable) : PhotoUiState
  data class Success(
    val listPhoto: List<PhotoUiItem>,
    val currentPage: Int,
    val isRefreshing: Boolean,
    val isLoadingNextPage: Boolean,
    val errorLoadNextPage: Throwable?
  ) : PhotoUiState
}

data class PhotoUiItem(
  val id: String? = null,
  val imageContent: String? = null,
  val username: String? = null,
  val likes: Int? = null,
  val des: String? = null,
  val imageProfile: String? = null,
)

fun PhotosResponseItem.toPhotoUiItem(): PhotoUiItem {
  return PhotoUiItem(
    id = id,
    des = description,
    imageContent = urls?.regular,
    username = user?.username,
    likes = likes,
    imageProfile = user?.profileImage?.large
  )
}
