package com.example.demo.unsplash.data.remote.interceptor

import com.example.demo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor: Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    return chain
      .request()
      .newBuilder()
      .addHeader("Authorization", "Client-ID ${BuildConfig.UNSPLASH_CLIENT_ID}")
      .build()
      .let(chain::proceed)
  }
}
