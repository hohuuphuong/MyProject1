package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(
    modifier: Modifier = Modifier,
    text: String = "",
    onClickSearch: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior,
    color: TopAppBarColors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp))
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = { Text(text = text, modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth()) },
        navigationIcon = {
            IconButton(onClick = onClickSearch) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.AccountBox,
                    contentDescription = null,
                )
            }
        },
        colors = color,
        scrollBehavior = scrollBehavior
    )
}



@Composable
fun NewsBottomNavigationBar(
    modifier: Modifier = Modifier,
    onClick1: () -> Unit = {},
    onClick2: () -> Unit = {},
    onClick3: () -> Unit = {},
    currentScreen: NewsScreen,
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
    ) {
        val navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedTextColor = Color.White,
            selectedIconColor = Color.White,
            indicatorColor = colorResource(R.color.light_blue)
        )
        NavigationBarItem(
            selected = (currentScreen == NewsScreen.HomeScreen),
            onClick =  onClick1,
            icon = {
                Icon(painter = painterResource(R.drawable.priority) , contentDescription = null, modifier.size(24.dp))
            },
            label = { Text(stringResource(R.string.ang_theo_d_i)) },
            colors = navigationBarItemColors
        )
        NavigationBarItem(
            selected = (currentScreen == NewsScreen.MainScreen),
            onClick = onClick2,
            icon = {
                Icon(painter = painterResource(R.drawable.globe) , contentDescription = null, modifier.size(24.dp))
            },
            label = { Text(stringResource(R.string.tin_chinh)) },
            colors = navigationBarItemColors
        )
        NavigationBarItem(
            selected = (currentScreen == NewsScreen.SavedScreen),
            onClick = onClick3,
            icon = {
                Icon(painter = painterResource(R.drawable.baseline_star_outline_24) , contentDescription = null)
            },
            label = { Text(stringResource(R.string.tin_b_i_l_u)) },
            colors = navigationBarItemColors
        )
    }
}