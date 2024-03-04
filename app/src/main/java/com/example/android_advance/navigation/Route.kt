package com.example.android_advance.navigation

import com.example.android_advance.utils.common.Constant

sealed class Route(val route: String) {
    object LoginScreen : Route(Constant.loginScreen)
    object ProductScreen : Route(Constant.productScreen)
    object WelcomeScreen : Route(Constant.welcomeScreen)
    object HomeNavigation : Route(Constant.homeNavigation)
    object HomeScreen : Route(Constant.homeScreen)
    object SignUpScreen : Route(Constant.signupScreen)

    object SearchScreen : Route(Constant.searchScreen)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}