package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val time: String,
    val isHotNews: Boolean,
    val categoryId: Int,
    val imageName: String?,
)
