package com.example.demo.unsplash.data.remote

import com.example.demo.unsplash.data.remote.response.CollectionItemResponse
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

  companion object {
    operator fun invoke(retrofit: Retrofit): UnsplashApiService = retrofit.create()
  }
}
