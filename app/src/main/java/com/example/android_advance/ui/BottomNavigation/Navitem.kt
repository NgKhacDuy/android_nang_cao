package com.example.android_advance.ui.BottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class Navitem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

val listOfNavItems: List<Navitem> = listOf(
    Navitem(
        label = "Message Screen",
        icon = Icons.Default.Message,
        route = Screens.MessageScreen.name
    ),
    Navitem(
        label = "Call Screen",
        icon = Icons.Default.Call,
        route = Screens.CallScreen.name
    ),
    Navitem(
        label = "Setting Screen",
        icon = Icons.Default.Settings,
        route = Screens.SettingScreen.name
    )
)