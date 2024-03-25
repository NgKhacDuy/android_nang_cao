package com.example.android_advance.navigation

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
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.ui.BottomNavigation.HomeNavigation
import com.example.android_advance.ui.BottomNavigation.listOfNavItems
import com.example.android_advance.ui.Group.CreateGroupScreen
import com.example.android_advance.ui.Home.HomeScreen
import com.example.android_advance.ui.Message.MessageScreen
import com.example.android_advance.ui.Screen.CallSreen
import com.example.android_advance.ui.Screen.SettingScreen
import com.example.android_advance.ui.SignUp.SignUpScreen
import com.example.android_advance.ui.call_history.SearchScreenPP
import com.example.android_advance.ui.login.LoginScreen
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
                        Log.e("RefreshToken", appSharedPreference.refreshToken)
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
                    CallSreen()
                }
                composable(route = Route.SettingScreen.route) {
                    SettingScreen(navController)
                }
                composable(route = Route.SearchScreen.route) {
                    SearchScreenPP(navController = navController)
                }
                composable(route = Route.CreateGroupScreen.route) {
                    CreateGroupScreen(navController = navController)
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
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            NestedNavigationGraphsGuideTheme {
//                val navController = rememberNavController()
//                NavHost(navController = navController, startDestination = "home") {
//                    composable("about") {
//
//                    }
//                    navigation(
//                        startDestination = "login",
//                        route = "auth"
//                    ) {
//                        composable("login") {
//                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)
//
//                            Button(onClick = {
//                                navController.navigate("calendar") {
//                                    popUpTo("auth") {
//                                        inclusive = true
//                                    }
//                                }
//                            }) {
//
//                            }
//                        }
//                        composable("register") {
//                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)
//                        }
//                        composable("forgot_password") {
//                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)
//                        }
//                    }
//                    navigation(
//                        startDestination = "calendar_overview",
//                        route = "calendar"
//                    ) {
//                        composable("calendar_overview") {
//
//                        }
//                        composable("calendar_entry") {
//
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}