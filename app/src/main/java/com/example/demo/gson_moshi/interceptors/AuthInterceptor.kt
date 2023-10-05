package com.example.demo.gson_moshi.interceptors

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class JwtTokenManager {
  suspend fun getJwtToken(): String? {
    delay(200)
    return "abcd"
  }

  suspend fun clear() {
    delay(200)
    println(">>> AuthInterceptor clear...")
  }
}

class AuthInterceptor(
  private val jwtTokenManager: JwtTokenManager,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    println(">>> AuthInterceptor START ${Thread.currentThread()}")

    val req = chain.request()

    // background thread in OkHttp....
    val jwtToken = runBlocking { jwtTokenManager.getJwtToken() }

    val response = req.newBuilder()
      .header("Authorization", "Bearer $jwtToken")
      .build()
      .let(chain::proceed)
      .also {
        println(">>> AuthInterceptor DONE ${Thread.currentThread()}")
      }

    if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
      runBlocking { jwtTokenManager.clear() }
    }

    return response
  }
}