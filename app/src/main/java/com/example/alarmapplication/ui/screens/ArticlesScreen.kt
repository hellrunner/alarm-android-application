package com.example.alarmapplication.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.alarmapplication.R
import com.example.alarmapplication.model.Article
import com.example.alarmapplication.ui.components.ArticleItem


@Composable
fun ArticlesScreen() {
    val articles = listOf(
        Article("Заголовок 1", "Краткое описание статьи 1", R.drawable.article_image1),
        Article("Заголовок 2", "Краткое описание статьи 2", R.drawable.article_image1),
        Article("Заголовок 1", "Краткое описание статьи 1", R.drawable.article_image1),
        Article("Заголовок 2", "Краткое описание статьи 2", R.drawable.article_image1),
        Article("Заголовок 1", "Краткое описание статьи 1", R.drawable.article_image1),
        Article("Заголовок 2", "Краткое описание статьи 2", R.drawable.article_image1)
//        ресурсы в res/drawable
    )

    LazyColumn {
        items(articles) { article ->
            ArticleItem(article)
        }
    }
}

//@Composable
//fun ArticlesScreen() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "Тут Будут Статьи")
//    }
//}
