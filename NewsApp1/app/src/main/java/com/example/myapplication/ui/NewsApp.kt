package com.example.myapplication.ui


import ArticlesState
import NewsViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.Category
import com.example.myapplication.ui.screens.MainScreen


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NewsApp() {
    val viewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory)
    val articlesState = viewModel.articlesState
    if(articlesState is ArticlesState.Success) {
        MainScreen(
            articles = articlesState.Articles ,
            savedArticles =  articlesState.Articles,
            typeOfNews = articlesState.Categories
        )
    }


}