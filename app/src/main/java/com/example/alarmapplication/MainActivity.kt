package com.example.alarmapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.alarmapplication.navigation.AppNavigation

/**
 * Главная активность приложения.
 *
 * Используется как контейнер для навигационной системы приложения, управляющей переходами между экранами.
 * Эта активность загружает и отображает пользовательский интерфейс, определённый в `AppNavigation`.
 *
 * Опт-ин аннотация [ExperimentalMaterial3Api] указывает, что в этом классе используются экспериментальные
 * элементы Material Design 3.
 */
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Инициализация и отображение навигационного компонента приложения
            AppNavigation()
        }
    }
}
