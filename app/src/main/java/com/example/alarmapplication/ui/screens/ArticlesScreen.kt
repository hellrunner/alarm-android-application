package com.example.alarmapplication.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.alarmapplication.R
import com.example.alarmapplication.model.Article
import com.example.alarmapplication.ui.components.ArticleItem


@Composable
fun ArticlesScreen() {
    val articles = listOf(
        Article(
            stringResource(R.string.article_title_1),
            stringResource(R.string.article_content_1),
            R.drawable.article_image1
        ),
        Article(
            stringResource(R.string.article_title_2),
            stringResource(R.string.article_content_2),
            R.drawable.article_image2
        ),
        Article(
            stringResource(R.string.article_title_3),
            stringResource(R.string.article_content_3),
            R.drawable.article_image3
        ),
        Article(
            stringResource(R.string.article_title_4),
            stringResource(R.string.article_content_4),
            R.drawable.article_image4
        ),
        Article(
            stringResource(R.string.article_title_5),
            stringResource(R.string.article_content_5),
            R.drawable.article_image5
        ),
        Article(
            stringResource(R.string.article_title_6),
            stringResource(R.string.article_content_6),
            R.drawable.article_image6
        )
    )

    LazyColumn {
        items(articles) { article ->
            ArticleItem(article)
        }
    }
}
