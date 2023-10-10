package com.example.demo.gson_moshi

import com.example.demo.BuildConfig
import com.example.demo.gson_moshi.interceptors.AuthInterceptor
import com.example.demo.gson_moshi.interceptors.CustomHeaderInterceptor
import com.example.demo.gson_moshi.interceptors.JwtTokenManager
import com.squareup.moshi.Moshi
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface JsonPlaceholderApiService {
  @GET("todos")
  suspend fun getTodos(): List<TodoItem>
}

fun buildOkHttpClient(): OkHttpClient {
  return OkHttpClient.Builder()
    .connectTimeout(20, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .writeTimeout(20, TimeUnit.SECONDS)
    .addInterceptor(CustomHeaderInterceptor())
    .addInterceptor(AuthInterceptor(JwtTokenManager()))
    .addNetworkInterceptor(
      HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
          HttpLoggingInterceptor.Level.BODY
        } else {
          HttpLoggingInterceptor.Level.NONE
        }
      }
    )
    .build()
}

fun buildRetrofit(
  baseUrl: String,
  okHttpClient: OkHttpClient,
  moshi: Moshi,
): Retrofit {
  return Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()
}
