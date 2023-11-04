package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.model.Article


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    article: Article,
    onClickBack: () -> Unit = {},
    onClickAdd: (Article) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                title = {},
                actions = {},
            )
        },
        bottomBar = {
            BottomAppBar {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { onClickAdd(article) },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_bookmark_add_24),
                        contentDescription = null
                    )
                }
                val context = LocalContext.current
                IconButton(
                    onClick = {
                        shareNews(context, article.title, article.title)
                    },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_more_vert_24),
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        DetailContent(modifier = Modifier.padding(it), article = article)
    }

}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    article: Article,
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(text = stringResource(R.string.bao_tuoi_tre))
        }
        item {
            Text(text = article.title, style = MaterialTheme.typography.headlineMedium)
        }
        item {
            Text(text = article.time + "", fontSize = 12.sp)
        }
        item {
            PhotoCard(imgSrc = article.imageName ?: "")
        }
        item {
            Text(text = article.content, style = MaterialTheme.typography.bodyLarge)
        }
    }
}



