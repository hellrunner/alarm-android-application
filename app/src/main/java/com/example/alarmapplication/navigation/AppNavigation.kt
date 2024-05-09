package com.example.alarmapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alarmapplication.alarm_View_Models.AlarmsViewModel
import com.example.alarmapplication.ui.screens.AlarmScreen
import com.example.alarmapplication.ui.screens.ArticlesScreen
import com.example.alarmapplication.ui.screens.MusicScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNavigation(alarmsViewModel: AlarmsViewModel) {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
                val currentDestination: NavDestination? = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = navItem.selectedIcon),
                                contentDescription = null
                            )
                        },
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.AlarmScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screens.AlarmScreen.name) {
                AlarmScreen(navController, alarmsViewModel)
            }
            composable(route = Screens.MusicScreen.name) {
                MusicScreen()
            }
            composable(route = Screens.ArticlesScreen.name) {
                ArticlesScreen()
            }
        }
    }
}