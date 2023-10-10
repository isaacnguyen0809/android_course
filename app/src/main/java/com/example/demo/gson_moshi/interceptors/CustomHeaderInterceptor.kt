package com.example.demo.gson_moshi.interceptors

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response

class CustomHeaderInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    println(">>> CustomHeaderInterceptor START")

    val request = chain.request()

    val newRequest = request.newBuilder()
      .header(
        "User-Agent",
        "Android/${Build.VERSION.SDK_INT}"
      )
      .build()

    val response = chain.proceed(newRequest)

    println(">>> CustomHeaderInterceptor DONE")

    return response
  }
}
