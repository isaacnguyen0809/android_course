package com.example.demo.unsplash.data.remote.response

data class SearchPhotoResult(
  val total: Int,
  val total_pages: Int,
  val results: List<CoverPhotoResponse>,
)
