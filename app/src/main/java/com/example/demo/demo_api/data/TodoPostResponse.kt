package com.example.demo.demo_api.data

data class TodoPostResponse(
  val title: String,
  val body: String,
  val userId: Long,
  val id: Long
)
