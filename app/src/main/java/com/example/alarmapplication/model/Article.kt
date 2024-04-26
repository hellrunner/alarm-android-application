package com.example.alarmapplication.model

import androidx.annotation.DrawableRes

/**
 * Класс данных, представляющий статью.
 *
 * @property title Заголовок статьи.
 * @property summary Краткое содержание статьи.
 * @property imageRes Идентификатор ресурса изображения для миниатюры статьи.
 */
data class Article(
    val title: String,       // Заголовок статьи.
    val summary: String,     // Краткое содержание статьи, предоставляющее обзор.
    @DrawableRes val imageRes: Int, // Идентификатор ресурса изображения, связанного со статьей.
)
