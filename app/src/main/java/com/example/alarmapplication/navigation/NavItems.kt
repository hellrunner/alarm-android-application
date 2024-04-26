package com.example.alarmapplication.navigation

import com.example.alarmapplication.R

/**
 * Определение элементов навигационного меню приложения.
 *
 * Класс `NavItems` содержит информацию об иконке и маршруте для каждого элемента меню.
 * Эти данные используются для отображения и навигации в пользовательском интерфейсе.
 *
 * @property selectedIcon Идентификатор ресурса иконки, которая отображается при выборе элемента меню.
 * @property route Строковый идентификатор маршрута, соответствующий экрану, на который должен происходить переход.
 */

data class NavItems(
//    var label: String,
    val selectedIcon: Int,
    val route: String
)

/**
 * Список элементов навигации, доступных в приложении.
 * Включает в себя экраны: будильник, музыка, статьи.
 */
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