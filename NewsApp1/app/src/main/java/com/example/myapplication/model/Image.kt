package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: Int = 0,
    val name: String = "",
    val articleId: Int
)
