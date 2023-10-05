package com.example.demo.gson_moshi

import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class TodoItem(
  @Json(name = "userId") val userId: Int, // 1
  @Json(name = "id") val id: Int, // 1
  @Json(name = "title") val title: String, // delectus aut autem
  @Json(name = "completed") val completed: Boolean // false
)