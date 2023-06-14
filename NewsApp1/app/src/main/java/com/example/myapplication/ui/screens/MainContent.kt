package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.model.Article
import com.example.myapplication.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    articlesByTypeOfNews: List<Article>,
    typeOfNews: List<Category>,
    onClickBottomBar: (String) -> Unit = {},
    onClickTopBar: (Category) -> Unit = {},
    onClickArticle: (Article) -> Unit = {},
    currentTypeOfNews: Category,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            Column() {
                NewsTopAppBar(
                    scrollBehavior = scrollBehavior,
                    text = stringResource(R.string.tin_chinh)
                )
                TopBarLazyRow(
                    typeOfNews = typeOfNews,
                    onClickTopBar = onClickTopBar,
                    currentTypeOfNews = currentTypeOfNews
                )
            }
        },
        bottomBar = {
            NewsBottomNavigationBar(
                currentScreen = NewsScreen.MainScreen,
                onClick1 = { onClickBottomBar(NewsScreen.HomeScreen.route) },
                onClick2 = {},
                onClick3 = { onClickBottomBar(NewsScreen.SavedScreen.route) }
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        GridMainScreen(
            modifier = Modifier.padding(it),
            articles = articlesByTypeOfNews,
            onClickArticle = onClickArticle
        )
    }
}

@Composable
fun TopBarLazyRow(
    modifier: Modifier = Modifier,
    typeOfNews: List<Category>,
    onClickTopBar: (Category) -> Unit = {},
    currentTypeOfNews: Category
) {
    Surface(tonalElevation = 8.dp) {
        LazyRow(modifier = modifier) {
            items(items = typeOfNews, key = { it.id }) { type ->
                TextButton(
                    onClick = { onClickTopBar(type) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White,
                        disabledContentColor = colorResource(R.color.light_blue)
                    ),
                    enabled = (currentTypeOfNews.id != type.id)
                ) {
                    Text(text = type.name)
                }
            }
        }
    }
}


@Composable
fun GridMainScreen (
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClickArticle: (Article) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(items = articles, key = { articles -> articles.id }) { article ->
            if(article.isHotNews)
                ListHotNewsItem(article = article, onClick = { onClickArticle(article) })
            else
                ListItem(article = article, onClick = { onClickArticle(article) })
        }
    }
}