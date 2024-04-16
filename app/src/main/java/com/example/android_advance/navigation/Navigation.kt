package com.example.android_advance.navigation

import InviteMember
import android.content.Context
import android.util.Log
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.ui.BottomNavigation.listOfNavItems
import com.example.android_advance.ui.Group.CreateGroupChat
import com.example.android_advance.ui.Group.ListUserGroup

import com.example.android_advance.ui.Home.HomeScreen
import com.example.android_advance.ui.Message.MessageScreen
import com.example.android_advance.ui.Screen.SettingScreen
import com.example.android_advance.ui.SignUp.SignUpScreen
import com.example.android_advance.ui.account.AccountScreen
import com.example.android_advance.ui.account.ChangePasswordScreen
import com.example.android_advance.ui.account.ManageAccountInfoScreen
import com.example.android_advance.ui.call_history.CallHistoryScreenPP
import com.example.android_advance.ui.call_history.SearchScreenPP
import com.example.android_advance.ui.login.LoginScreen
import com.example.android_advance.ui.videoCall.VideoScreen
import com.example.android_advance.ui.welcome.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val bottomBarVisible = rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current
    val appSharedPreference = AppSharedPreference(context)
    Scaffold(
        bottomBar = {
            if (bottomBarVisible.value) {
                NavigationBar {
                    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
                    val currentDestination: NavDestination? = navBackStackEntry?.destination

                    listOfNavItems.forEach { Navitem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == Navitem.route } == true,
                            onClick = {
                                navController.navigate(Navitem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = Navitem.icon,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = Navitem.label)
                            }
                        )
                    }
                }
            }
        }) { paddingValues: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = "auth",
            modifier = Modifier.padding(paddingValues)
        ) {
            navigation(startDestination = Route.WelcomeScreen.route, route = "auth") {
                composable(route = Route.SignUpScreen.route) {
                    bottomBarVisible.value = false
                    SignUpScreen(navController)
                }
                composable(route = Route.LoginScreen.route) {
                    bottomBarVisible.value = false
                    LoginScreen(navController)
                }
                composable(route = Route.WelcomeScreen.route) {
                    if (appSharedPreference.accessToken == "" && appSharedPreference.refreshToken == "") {
                        bottomBarVisible.value = false
                        WelcomeScreen(navController)
                    } else {
                        navController.navigate("home") {
                            popUpTo("auth") {
                                inclusive = true
                            }
                        }
                    }
                }
            }
            navigation(startDestination = Route.RoomScreen.route, route = "home") {
                composable(route = Route.RoomScreen.route) {
                    bottomBarVisible.value = true
                    HomeScreen(navController)
                }
                composable(route = Route.CallScreen.route) {
                    CallHistoryScreenPP(navController)
                }
                composable(route = Route.SettingScreen.route) {
                    SettingScreen(navController)
                }
                composable(route = Route.SearchScreen.route) {
                    SearchScreenPP(navController = navController)
                }
                composable(route = Route.CreateGroupScreen.route) {
                    CreateGroupChat(navController = navController)
                }
                composable(route = Route.VideoScreen.route + "/{roomName}", arguments = listOf((
                        navArgument("roomName") {
                            type = NavType.StringType
                            nullable = false
                        }
                        ))) {
                    val roomName =
                        it.arguments?.getString("roomName") ?: return@composable
                    VideoScreen(roomName = roomName, navController)
                }
                composable(route = Route.AccountScreen.route) {
                    AccountScreen(navController)
                }
                composable(route = Route.ManageAccountInfoScreen.route)
                {
                    ManageAccountInfoScreen(navController)
                }
                composable(route = Route.ChangePasswordScreen.route)
                {
                    ChangePasswordScreen(navController = navController)
                }
                composable(route = Route.listUserGroupScreen.route + "/{roomId}", arguments = listOf((
                        navArgument("roomId") {
                            type = NavType.StringType
                            nullable = false
                        }
                        ))) {
                    val roomId =
                        it.arguments?.getString("roomId") ?: return@composable
                    ListUserGroup(navController = navController, idRoom = roomId)
                }
                composable(route = Route.InviteMemberScreen.route){
                    InviteMember(navController = navController)
                }
                composable(
                    route = Route.MessageScreen.route + "/{idRoom}/{namePartner}",
                    arguments = listOf(
                        navArgument("idRoom") {
                            type = NavType.StringType
                            nullable = false
                        },
                        navArgument("namePartner") {
                            type = NavType.StringType
                            nullable = false
                        },
                    )
                ) {
                    bottomBarVisible.value = false

                    it.arguments?.getString("idRoom")
                        ?.let { it1 ->
                            MessageScreen(
                                idRoom = it1,
                                navController,
                                it.arguments?.getString("namePartner")!!
                            )
                        }
                    LaunchedEffect(Unit) {
                        navController.addOnDestinationChangedListener { _, destination, _ ->
                            if (destination.route != Route.MessageScreen.route) {
                                bottomBarVisible.value = true
                            }
                        }
                    }
                }
            }
        }
    }
}

fun isHaveToken(context: Context): Boolean {
    val appSharedPreference = AppSharedPreference(context)
    return appSharedPreference.accessToken != "" && appSharedPreference.refreshToken != ""
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}