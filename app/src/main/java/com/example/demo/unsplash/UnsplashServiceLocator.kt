package com.example.demo.unsplash

import androidx.annotation.MainThread
import com.example.demo.BuildConfig
import com.example.demo.unsplash.data.remote.UnsplashApiService
import com.example.demo.unsplash.data.remote.interceptor.AuthorizationInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object UnsplashServiceLocator {
  const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"

  // @MainThread
  private var _application: UnsplashApplication? = null

  @MainThread
  fun initWith(app: UnsplashApplication) {
    _application = app
  }

  private val moshi: Moshi by lazy {
    Moshi.Builder()
      .addLast(KotlinJsonAdapterFactory())
      .build()
  }

  private val httpLoggingInterceptor
    get() = HttpLoggingInterceptor().apply {
      level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
      } else {
        HttpLoggingInterceptor.Level.NONE
      }
    }

  private val authorizationInterceptor: AuthorizationInterceptor
    get() = AuthorizationInterceptor()

  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(UNSPLASH_BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
  }

  val okHttpClient: OkHttpClient by lazy {
    // TODO: Add auth interceptor

    OkHttpClient.Builder()
      .connectTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .addNetworkInterceptor(httpLoggingInterceptor)
      .addInterceptor(authorizationInterceptor)
      .build()
  }

  val unsplashApiService: UnsplashApiService by lazy { UnsplashApiService(retrofit) }

  val application: UnsplashApplication
    get() = checkNotNull(_application) {
      "UnsplashServiceLocator must be initialized. " +
        "Call UnsplashServiceLocator.initWith(this) in your Application class."
    }
}
