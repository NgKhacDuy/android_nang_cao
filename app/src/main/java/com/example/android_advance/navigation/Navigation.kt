package com.example.android_advance.navigation

import InviteMember
import OptionsMenu
import android.content.Context
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
import com.example.android_advance.ui.Group.CreateGroupScreen
import com.example.android_advance.ui.ListUser.ListUserGroup
import com.example.android_advance.ui.Home.HomeScreen
import com.example.android_advance.ui.Message.MessageScreen
import com.example.android_advance.ui.Screen.SettingScreen
import com.example.android_advance.ui.SignUp.SignUpScreen
import com.example.android_advance.ui.SignUp.VerifyOTPScreenRegister
import com.example.android_advance.ui.account.AccountScreen
import com.example.android_advance.ui.account.ChangePasswordScreen
import com.example.android_advance.ui.account.ManageAccountInfoScreen
import com.example.android_advance.ui.call_history.CallHistoryScreenPP
import com.example.android_advance.ui.call_history.SearchScreenPP
import com.example.android_advance.ui.login.LoginScreen
import com.example.android_advance.ui.menu_option.MenuOption
import com.example.android_advance.ui.splash.SplashScreen
import com.example.android_advance.ui.videoCall.VideoScreen
import com.example.android_advance.ui.welcome.WelcomeScreen
import com.example.android_advance.voice_to_text.VoiceToTextParser
import com.example.android_advance.ui.forgotPassword.EnterNumberScreen
import com.example.android_advance.ui.forgotPassword.ResetPasswordScreen
import com.example.android_advance.ui.forgotPassword.VerifyOTPScreen


@Composable
fun Navigation(voiceToTextParser: VoiceToTextParser) {
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
            navigation(startDestination = Route.SplashScreen.route, route = "auth") {
                composable(route = Route.SplashScreen.route) {
                    bottomBarVisible.value = false
                    SplashScreen(navController)
                }
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
                composable(route = Route.ChangePasswordScreen.route)
                {
                    ChangePasswordScreen(navController = navController)
                }
                //

                composable(route = Route.ForgetPassword1.route)
                {
                    EnterNumberScreen(navController = navController)
                }
                composable(route = Route.ForgetPassword3.route + "/{userId}", arguments = listOf((
                        navArgument("userId") {
                            type = NavType.StringType
                            nullable = false
                        }
                        )))
                {
                    val userId =
                        it.arguments?.getString("userId") ?: return@composable
                    ResetPasswordScreen(navController = navController, userId = userId)
                }
                composable(
                    route = Route.ForgetPassword2.route + "/{userId}/{sendOtp}",
                    arguments = listOf(
                        navArgument("userId") {
                            type = NavType.StringType
                            nullable = false
                        },
                        navArgument("sendOtp") {
                            type = NavType.StringType
                            nullable = false
                        },
                    )
                ) {
                    val userId =
                        it.arguments?.getString("userId") ?: return@composable
                    val sendOtp =
                        it.arguments?.getString("sendOtp") ?: return@composable
                    VerifyOTPScreen(navController, userId, sendOtp)
                }

                composable(
                    route = Route.OtpRegisterScreen.route + "/{sendOtp}",
                    arguments = listOf(
                        navArgument("sendOtp") {
                            type = NavType.StringType
                            nullable = false
                        },
                    )
                ) {
                    val sendOtp =
                        it.arguments?.getString("sendOtp") ?: return@composable
                    VerifyOTPScreenRegister(navController, sendOtp)
                }
            }
            navigation(startDestination = Route.RoomScreen.route, route = "home") {
                composable(route = Route.RoomScreen.route) {
                    bottomBarVisible.value = true
                    HomeScreen(navController)
                }
                composable(route = Route.CallScreen.route) {
                    bottomBarVisible.value = true
                    CallHistoryScreenPP(navController)
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
                //
                composable(
                    route = Route.MessageScreen.route,
                ) {
                    bottomBarVisible.value = false
                    MessageScreen(
                        navController = navController,
                        voiceToTextParser
                    )
                    LaunchedEffect(Unit) {
                        navController.addOnDestinationChangedListener { _, destination, _ ->
                            if (destination.route != Route.MessageScreen.route) {
                                bottomBarVisible.value = true
                            }
                        }
                    }
                }
                composable(
                    route = Route.MenuOption.route + "/{isGroup}/{avatar}", arguments = listOf(
                        navArgument("isGroup") {
                            type = NavType.StringType
                            nullable = false
                        },
                        navArgument("avatar") {
                            type = NavType.StringType
                            nullable = false
                        },
                    )
                ) {
                    bottomBarVisible.value = false
                    MenuOption(
                        navController = navController,
                        (it.arguments?.getString("isGroup") ?: "false").toBoolean(),
                        it.arguments?.getString("avatar") ?: ""
                    )
                }

                composable(Route.ListUserInGroup.route) {
                    bottomBarVisible.value = false
                    ListUserGroup(navController = navController)
                }

                composable(Route.InviteMemberScreen.route) {
                    bottomBarVisible.value = false
                    InviteMember(navController = navController)
                }

                // this is the main option menu
                composable(
                    route = Route.OptionsMenuChat.route + "/{idRoom}/{partnerName}",
                    arguments = listOf(
                        navArgument("idRoom") {
                            type = NavType.StringType
                            nullable = false
                        },
                        navArgument("partnerName") {
                            type = NavType.StringType
                            nullable = false
                        }

                    )
                ) {
                    bottomBarVisible.value = false
                    OptionsMenu(
                        navController = navController,
                        (it.arguments?.getString("idRoom") ?: "no id room"),
                        it.arguments?.getString("partnerName") ?: "no partner name"
                    )
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