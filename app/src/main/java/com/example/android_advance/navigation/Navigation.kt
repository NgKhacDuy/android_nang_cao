package com.example.android_advance.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.ui.BottomNavigation.HomeNavigation
import com.example.android_advance.ui.Home.HomeScreen
import com.example.android_advance.ui.SignUp.SignUpScreen
import com.example.android_advance.ui.login.LoginScreen
import com.example.android_advance.ui.welcome.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Route.WelcomeScreen.route) {
        composable(route = Route.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Route.WelcomeScreen.route) {
            if (!isHaveToken(context)) {
                WelcomeScreen(navController)
            } else {
                HomeNavigation()
            }
        }
        composable(route = Route.HomeNavigation.route) {
            HomeScreen()
        }
        composable(route = Route.SignUpScreen.route) {
            SignUpScreen(navController)
        }

    }


}

fun isHaveToken(context: Context): Boolean {
    val appSharedPreference = AppSharedPreference(context)
    return appSharedPreference.accessToken != "" && appSharedPreference.refreshToken != ""
}