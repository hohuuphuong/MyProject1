package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.model.Article


@Composable
fun SavedArticleContent(
    modifier: Modifier = Modifier,
    savedArticles: List<Article>,
    onClickArticle: (Article) -> Unit = {},
    onClickBottomNav: (String) -> Unit = {},
    onClickBack: () -> Unit = {},
    onClickUnadd: (Article) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NewsBottomNavigationBar(
                onClick1 = { onClickBottomNav(NewsScreen.HomeScreen.route) },
                onClick2 = { onClickBottomNav(NewsScreen.MainScreen.route) } ,
                onClick3 = {},
                currentScreen = NewsScreen.SavedScreen
            )
        }
    ) {
        GridSavedScreen(
            modifier = Modifier.padding(it),
            articles = savedArticles,
            onClickArticle = onClickArticle,
            onClickBack = onClickBack,
            onClickUnadd = onClickUnadd
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridSavedScreen(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClickArticle: (Article) -> Unit = {},
    onClickBack: () -> Unit = {},
    onClickUnadd: (Article) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onClickBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.saved_articles),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth()
                )
            },
            actions = { Spacer(modifier = Modifier.width(20.dp)) },
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = 8.dp
        ) {
            LazyColumn() {
                items(items = articles) {article ->
                    val context = LocalContext.current
                    SavedArticleListItem(
                        article = article,
                        onClickArticle = { onClickArticle(article) },
                        onClickMoreVert = { shareNews(context, article.title, article.title) },
                        onClickUnadd = { onClickUnadd(article) }
                    )
                }
            }
        }
    }
}


@Composable
fun SavedArticleListItem(
    modifier: Modifier = Modifier,
    article: Article,
    onClickArticle: () -> Unit,
    onClickMoreVert: () -> Unit = {},
    onClickUnadd: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClickArticle
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(article.imageName)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = article.time,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Row {
                    IconButton(
                        onClick = onClickUnadd,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_bookmark_added_24),
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = onClickMoreVert,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_more_vert_24),
                            contentDescription = null
                        )
                    }
                }
                
            }
        }
    }
}



