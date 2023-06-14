package com.example.myapplication.ui.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClickBottomNav: (String) -> Unit = {},
    onClickExpand: (NewsScreen) -> Unit = {},
    onClickArticle: (Article) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            NewsTopAppBar(
                scrollBehavior = scrollBehavior,
                text = stringResource(R.string.news_app)
        ) },
        bottomBar = {
            NewsBottomNavigationBar(
                onClick1 = {},
                onClick2 = { onClickBottomNav(NewsScreen.MainScreen.route) },
                onClick3 = { onClickBottomNav(NewsScreen.SavedScreen.route) },
                currentScreen = NewsScreen.HomeScreen
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        GridHomeScreen(
            modifier = Modifier.padding(it),
            articles = articles, onClickArticle = onClickArticle,
            onClickExpand = onClickExpand
        )
    }
}



@Composable
fun GridHomeScreen (
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClickArticle: (Article) -> Unit = {},
    onClickExpand: (NewsScreen) -> Unit = {}
) {
    val listHotNews = articles.filter {
        it.isHotNews
    }
    val listForYou = articles.filter {
        (it.title.contains("game"))
    }
    LazyColumn(modifier = modifier) {
        item {
            Category(textCategory = stringResource(R.string.tin_bai_hang_dau), onClick = onClickExpand)
        }
        val iterator = listHotNews.iterator()
        for(i in 1..3) {
            if(iterator.hasNext()) {
                val article = iterator.next()
                item {
                    ListHotNewsItem(article = article, onClick = { onClickArticle(article) })
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Category(textCategory = stringResource(R.string.tin_bai_danh_cho_ban))
        }
        items(items = listForYou, key = { it.id }) { article ->
            if(article.isHotNews)
                ListHotNewsItem(article = article, onClick = {onClickArticle(article)})
            else
                ListItem(article = article, onClick = {onClickArticle(article)})
        }
    }
//    val grouped = articles.groupBy {
////        it.id_typeOfNews
////    }
//    LazyColumn(
//        modifier = modifier,
//    ) {
//        grouped.forEach {
//            item {
//                Category(textCategory = it.key.toString())
//            }
//            items(items = it.value, key = { articles -> articles.id }) { article ->
//                ListItem(article = article, onClick = onClick)
//            }
//            item {
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }
//    }
}



@Composable
fun ListItem(modifier: Modifier = Modifier, article: Article, onClick: () -> Unit = {}) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        tonalElevation = 8.dp,
        onClick = onClick
    ) {
        Column {
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(4f)
                ) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = article.time,
                        fontSize = 10.sp
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                PhotoCard(imgSrc = article.imageName ?: "", modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f))
            }
        }
    }
}

@Composable
fun ListHotNewsItem(modifier: Modifier = Modifier, article: Article, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shadowElevation = 8.dp,
        tonalElevation = 8.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PhotoCard(modifier = Modifier
                .aspectRatio(2f)
                .padding(0.dp), imgSrc = article.imageName ?: "")
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = article.time,
                fontSize = 10.sp,
            )
        }
    }
}




@Composable
fun PhotoCard(modifier: Modifier = Modifier, imgSrc: String) {
    Card(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imgSrc)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun Category(textCategory: String, modifier: Modifier = Modifier, onClick: (NewsScreen) -> Unit = {}) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = textCategory,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )
            IconButton(onClick = {onClick(NewsScreen.MainScreen)}) {
                Icon(
                    painter = painterResource(R.drawable.chevron_right_fill0_wght300_grad0_opsz48),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

