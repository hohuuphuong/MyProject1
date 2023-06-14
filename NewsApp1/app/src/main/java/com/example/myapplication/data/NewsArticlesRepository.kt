package com.example.myapplication.data

import com.example.myapplication.model.Article
import com.example.myapplication.model.Category
import com.example.myapplication.network.NewsApiService

interface NewsArticlesRepository {
    suspend fun getArticles(): List<Article>
}

class NetworkNewsArticlesRepository(private val newsApiService: NewsApiService): NewsArticlesRepository {
    override suspend fun getArticles(): List<Article> {
        return newsApiService.getArticles()
    }
}

class NetworkCategoriesRepository(private val newsApiService: NewsApiService) {
    suspend fun getCategories(): List<Category> {
        return  newsApiService.getCategories()
    }
}
