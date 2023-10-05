package com.example.demo.data

import com.google.gson.annotations.SerializedName

data class TodoResponse (
    @SerializedName("userId")
    val userID: Long,

    val id: Long,
    val title: String,
    val completed: Boolean
)

