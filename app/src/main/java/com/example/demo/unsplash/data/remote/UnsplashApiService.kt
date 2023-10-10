package com.example.demo.unsplash.data.remote

import retrofit2.Retrofit
import retrofit2.create

interface UnsplashApiService {
  // TODO: Add API endpoints here

  companion object {
    operator fun invoke(retrofit: Retrofit): UnsplashApiService = retrofit.create()
  }
}
