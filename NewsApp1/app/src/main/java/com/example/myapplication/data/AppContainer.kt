package com.example.myapplication.data

import com.example.myapplication.network.NewsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer {
    val newsRepository: NewsArticlesRepository
    val networkCategoriesRepository: NetworkCategoriesRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "http://10.0.2.2:5165/api/Articles/"


    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofit2: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("http://10.0.2.2:5165/api/")
        .build()

    private val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }

    private val retrofitService2: NewsApiService by lazy {
        retrofit2.create(NewsApiService::class.java)
    }
    override val newsRepository: NewsArticlesRepository by lazy {
        NetworkNewsArticlesRepository(retrofitService)
    }

    override val networkCategoriesRepository: NetworkCategoriesRepository by lazy {
        NetworkCategoriesRepository(retrofitService2)
    }
}

