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
        Article(
            "Регулярность важна",
            " Старайтесь ложиться спать и просыпаться в одно и то же время каждый день. Это помогает установить ваш биологический час и улучшить качество сна.",
            R.drawable.article_image1
        ),
        Article(
            "Температура имеет значение",
            "Идеальная температура для сна - около 18°C (64°F). Чрезмерно теплая или холодная комната может нарушить ваш сон.",
            R.drawable.article_image2
        ),
        Article(
            "Технологии могут мешать",
            " Синий свет от экранов телефонов, планшетов и компьютеров может подавлять выработку мелатонина, гормона, который помогает вам засыпать.",
            R.drawable.article_image3
        ),
        Article(
            "Дневные сны должны быть короткими",
            "Длинные дневные сны могут нарушить ночной сон. Если вам нужен дневной сон, лучше всего ограничить его 20-30 минутами.",
            R.drawable.article_image4
        ),
        Article(
            "Физическая активность способствует лучшему сну",
            "Физическая активность способствует лучшему сну",
            R.drawable.article_image5
        ),
        Article(
            "Ограничение потребления кофеина и алкоголя",
            "Избегайте кофеина и алкоголя за несколько часов до сна, так как они могут нарушить ваш сон.",
            R.drawable.article_image6
        )
//        ресурсы в res/drawable
    )

    LazyColumn() {
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
