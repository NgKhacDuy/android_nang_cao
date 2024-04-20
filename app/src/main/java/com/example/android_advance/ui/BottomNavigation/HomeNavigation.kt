package com.example.android_advance.ui.BottomNavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_advance.ui.Group.CreateGroupScreen
import com.example.android_advance.ui.Home.HomeScreen
import com.example.android_advance.ui.Message.MessageScreen
import com.example.android_advance.ui.Screen.CallSreen
import com.example.android_advance.ui.Screen.SettingScreen
import com.example.android_advance.ui.call_history.CallHistoryScreenPP
import com.example.android_advance.ui.call_history.SearchScreenPP
import com.example.android_advance.ui.welcome.WelcomeScreen

@Composable
fun HomeNavigation() {
//    val navController: NavHostController = rememberNavController()
//    val bottomBarVisible = rememberSaveable { mutableStateOf(true) } // Initial state is true
//
//    Scaffold(
//        bottomBar = {
//            if (bottomBarVisible.value) {
//                NavigationBar {
//                    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
//                    val currentDestination: NavDestination? = navBackStackEntry?.destination
//
//                    listOfNavItems.forEach { Navitem ->
//                        NavigationBarItem(
//                            selected = currentDestination?.hierarchy?.any { it.route == Navitem.route } == true,
//                            onClick = {
//                                navController.navigate(Navitem.route) {
//                                    popUpTo(navController.graph.findStartDestination().id) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
//                            },
//                            icon = {
//                                Icon(
//                                    imageVector = Navitem.icon,
//                                    contentDescription = null
//                                )
//                            },
//                            label = {
//                                Text(text = Navitem.label)
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    ) { paddingValues: PaddingValues ->
//        NavHost(
//            navController = navController,
//            startDestination = ChildRoute.RoomScreen.route,
//            modifier = Modifier
//                .padding(paddingValues)
//        ) {
//            composable(route = ChildRoute.RoomScreen.route) {
//                bottomBarVisible.value = true
//                HomeScreen(navController)
//            }
//            composable(route = ChildRoute.CallScreen.route) {
//                CallHistoryScreenPP(navController)
//            }
//            composable(route = ChildRoute.SettingScreen.route) {
//                SettingScreen(navController)
//            }
//            composable(route = ChildRoute.SearchScreen.route) {
//                SearchScreenPP(navController = navController)
//            }
//            composable(route = ChildRoute.CreateGroup.route) {
//                CreateGroupScreen(navController)
//            }
//            composable(
//                route = ChildRoute.MessageScreen.route + "/{idRoom}/{namePartner}", arguments = listOf(
//                    navArgument("idRoom") {
//                        type = NavType.StringType
//                        nullable = false
//                    },
//                    navArgument("namePartner") {
//                        type = NavType.StringType
//                        nullable = false
//                    },
//                )
//            ) {
//                bottomBarVisible.value = false
//
//                it.arguments?.getString("idRoom")
//                    ?.let { it1 ->
//                        MessageScreen(idRoom = it1, navController, it.arguments?.getString("namePartner")!!)
//                    }
//                LaunchedEffect(Unit) {
//                    navController.addOnDestinationChangedListener { _, destination, _ ->
//                        if (destination.route != ChildRoute.MessageScreen.route) {
//                            bottomBarVisible.value = true
//                        }
//                    }
//                }
//            }
//        }
//    }
}