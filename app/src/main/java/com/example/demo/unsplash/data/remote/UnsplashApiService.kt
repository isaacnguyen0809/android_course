package com.example.demo.unsplash.data.remote

import com.example.demo.unsplash.data.remote.response.CollectionItemResponse
import com.example.demo.unsplash.data.remote.response.PhotosResponseItem
import com.example.demo.unsplash.data.remote.response.SearchPhotoResult
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {
  // TODO: Add API endpoints here

  @GET("collections")
  suspend fun getCollections(
    @Query("page") page: Int,
    @Query("per_page") perPage: Int,
  ): List<CollectionItemResponse>

  @GET("search/photos")
  suspend fun searchPhotos(
    @Query("query") query: String,
    @Query("page") page: Int,
    @Query("per_page") perPage: Int,
  ): SearchPhotoResult

  @GET("photos")
  suspend fun getPhotos(
    @Query("order_by") orderBy: String,
    @Query("page") page: Int,
    @Query("per_page") perPage: Int,
  ): List<PhotosResponseItem>


  companion object {
    operator fun invoke(retrofit: Retrofit): UnsplashApiService = retrofit.create()
  }
}
