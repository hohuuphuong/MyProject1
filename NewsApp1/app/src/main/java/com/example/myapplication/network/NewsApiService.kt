package com.example.myapplication.network

import com.example.myapplication.model.Article
import com.example.myapplication.model.Category
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApiService {
    @GET("getArticlesVM2")
    suspend fun getArticles(): List<Article>

    @GET("Categorys")
    suspend fun getCategories(): List<Category>
}
