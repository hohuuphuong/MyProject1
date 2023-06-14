package com.example.myapplication.ui.screens

import NewsViewModel
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.model.Article
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.myapplication.model.Category



@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    savedArticles: List<Article>,
    typeOfNews: List<Category>,
    navController: NavHostController =  rememberNavController(),
    viewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    val groupedByTypeOfNews = articles.groupBy {
        it.categoryId
    }
    NavHost(
        navController = navController,
        startDestination = NewsScreen.HomeScreen.route,
        modifier = modifier,
    ) {
        composable(route = NewsScreen.HomeScreen.route) {
            HomeContent(
                articles = articles,
                onClickBottomNav = {
                    viewModel.resetUiState()
                    navController.navigate(it) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                },
                onClickExpand = {
                    navController.navigate(it.route) {
                        popUpTo(NewsScreen.HomeScreen.route)
                    }
                },
                onClickArticle = {
                    navController.navigate(NewsScreen.DetailScreen.passId(it.id))
                }
            )
        }
        composable(
            route = NewsScreen.MainScreen.route,
            arguments = listOf(
                navArgument("id_typeOfNews") {
                    type = NavType.IntType
                    defaultValue = typeOfNews[0].id
                }
            )
        ) {
            val articlesByTypeOfNews = groupedByTypeOfNews.getOrDefault(it.arguments?.getInt("id_typeOfNews"), listOf())
            MainContent(
                articlesByTypeOfNews = articlesByTypeOfNews,
                typeOfNews = typeOfNews,
                onClickBottomBar = {
                    navController.navigate(it) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id)
                        viewModel.resetUiState()
                    }
                },
                onClickTopBar = {
                    viewModel.setCurrentTypeOfNews(it)
                    navController.navigate(NewsScreen.MainScreen.passId(it.id)) { launchSingleTop = true }
                },
                onClickArticle = { navController.navigate(NewsScreen.DetailScreen.passId(it.id)) },
                currentTypeOfNews = uiState.currentTypeOfNews
            )
        }
        composable(route = NewsScreen.SavedScreen.route) {
            SavedArticleContent(
                savedArticles = savedArticles,
                onClickArticle = { navController.navigate(NewsScreen.DetailScreen.passId(it.id)) },
                onClickBottomNav = {
                    viewModel.resetUiState()
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                },
                onClickBack = { navController.navigateUp() },
                onClickUnadd = { viewModel.onClickUnadd(it) }
            )
        }
        composable(
            route = NewsScreen.DetailScreen.route,
            arguments = listOf(
                navArgument("id_article") { type = NavType.IntType }
            )
        ) {
            val id = it.arguments?.getInt("id_article") ?: 0
            DetailScreen(
                article = articles.find { it.id == id }!!,
                onClickBack = { navController.navigateUp() },
                onClickAdd = { viewModel.onClickAdd(it) }
            )
        }
    }
}





fun shareNews(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            ""
        )
    )
}




sealed class NewsScreen(val route: String) {
    object HomeScreen: NewsScreen("home_screen")
    object MainScreen: NewsScreen("main_screen?id_typeOfNews={id_typeOfNews}") {
        fun passId(id: Int = 1): String {
            return "main_screen?id_typeOfNews=$id"
        }
    }
    object SavedScreen: NewsScreen("saved_screen")
    object DetailScreen: NewsScreen("detail_screen/{id_article}") {
        fun passId(id: Int = 0): String {
            return "detail_screen/$id"
        }
    }
}







