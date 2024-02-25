package com.example.alarmapplication.navigation

import com.example.alarmapplication.R

data class NavItems(
//    var label: String,
    val selectedIcon: Int,
    val route: String
)

val listOfNavItems: List<NavItems> = listOf(
    NavItems(
//        label = "AlarmScreen",
        selectedIcon = R.drawable.icon_alarm,
        route = Screens.AlarmScreen.name
    ), NavItems(
//        label = "MusicScreen",
        selectedIcon = R.drawable.icon_note,
        route = Screens.MusicScreen.name
    ), NavItems(
//        label = "ArticlesScreen",
        selectedIcon = R.drawable.icon_book,
        route = Screens.ArticlesScreen.name
    )
)