package com.example.android_advance.ui.BottomNavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_advance.ui.Screen.CallSreen
import com.example.android_advance.ui.Screen.MessageScreen
import com.example.android_advance.ui.Screen.SettingScreen

@Composable
fun HomeNavigation(){
    val navController : NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry : NavBackStackEntry? by navController.currentBackStackEntryAsState()
                val currentDestination : NavDestination? = navBackStackEntry?.destination

                listOfNavItems.forEach { Navitem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == Navitem.route } == true,
                        onClick = {
                            navController.navigate(Navitem.route){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Navitem.icon,
                                contentDescription = null)
                        },
                        label = {
                            Text(text = Navitem.label)
                        }
                    )
                }
            }
        }
    ) {paddingValues: PaddingValues ->
        NavHost(navController = navController,
            startDestination = Screens.MessageScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ){
            composable(route = Screens.MessageScreen.name){
                MessageScreen()
            }
            composable(route = Screens.CallScreen.name){
                CallSreen()
            }
            composable(route = Screens.SettingSreen.name){
                SettingScreen()
            }
        }
    }
}