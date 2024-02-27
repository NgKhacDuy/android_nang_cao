package com.example.android_advance.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_advance.ui.SignUp.SignUpScreen
import com.example.android_advance.ui.login.LoginScreen
import com.example.android_advance.ui.welcome.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.HomeNavigation.route) {
        composable(route = Route.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Route.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(route = Route.HomeNavigation.route) {
            WelcomeScreen(navController)
        }
        composable(route = Route.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        
    }
}