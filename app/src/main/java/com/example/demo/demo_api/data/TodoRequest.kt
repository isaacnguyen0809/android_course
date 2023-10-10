package com.example.demo.demo_api.data

data class TodoRequest(
  val title: String,
  val body: String,
  val userId: Long
)
