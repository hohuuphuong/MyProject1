package com.example.myapplication.data

import com.example.myapplication.model.Category

data class NewsUiState(
    val currentTypeOfNews: Category = Category(id = 1, name = ""),
)