package com.example.alarmapplication.model

import androidx.annotation.DrawableRes

data class Article(
    val title: String,
    val summary: String,
    @DrawableRes val imageRes: Int,
)
