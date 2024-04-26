package com.example.alarmapplication.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


/**
 * Экран детализации статьи.
 *
 * Отображает заголовок статьи в виде текста. Этот компонент предназначен для использования
 * в качестве экрана подробного просмотра статьи, куда пользователь может перейти для чтения полного текста.
 *
 * @param articleTitle Заголовок статьи, который будет отображен на экране.
 */
@Composable
fun ArticleDetailScreen(articleTitle: String) {
    Text(text = articleTitle)
}

/**
 * Превью экрана детализации статьи.
 *
 * Демонстрирует предварительный просмотр компонента `ArticleDetailScreen` с примером заголовка статьи.
 * Используется для визуальной проверки и тестирования визуальных компонентов в среде разработки.
 */
@Preview
@Composable
fun PreviewArticleDetailScreen() {
    ArticleDetailScreen(articleTitle = "Пример статьи")
}