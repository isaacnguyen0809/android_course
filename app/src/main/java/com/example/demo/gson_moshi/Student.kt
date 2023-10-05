package com.example.demo.gson_moshi

import com.squareup.moshi.Json
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Student(
  @SerializedName("id")
  @Json(name = "id") val id: String, // c5e79118-5c4f-4ad5-a554-8a2516ae42d1
  @SerializedName("first_name")
  @Json(name = "first_name") val firstName: String, // Araceli Meyer
  @SerializedName("last_name")
  @Json(name = "last_name") val lastName: String, // Bonnie Mendez
  @SerializedName("age")
  @Json(name = "age") val age: Int // 21
)