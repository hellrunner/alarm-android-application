package com.example.alarmapplication.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ArticleDetailScreen(articleTitle: String) {
    Text(text = articleTitle)
}

@Preview
@Composable
fun PreviewArticleDetailScreen() {
    ArticleDetailScreen(articleTitle = "Пример статьи")
}