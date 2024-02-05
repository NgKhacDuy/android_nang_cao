package com.example.android_advance.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android_advance.ui.BottomNavigation.HomeNavigation
import com.example.android_advance.ui.login.LoginScreen
import com.example.android_advance.ui.product.ProductScreen
import com.example.android_advance.ui.welcome.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.HomeNavigation.route) {
        composable(route = Route.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Route.WelcomeScreen.route) {
            WelcomeScreen()
        }
        composable(route = Route.HomeNavigation.route) {
            HomeNavigation()
        }
        composable(
            route = Route.ProductScreen.route + "/{username}/{password}", arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("password") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }
            )
        ) { entry ->
            ProductScreen(
                username = entry.arguments?.getString("username")!!,
                password = entry.arguments?.getString("password")!!,
                navController
            )
        }
    }
}