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
        label = "Message",
        icon = Icons.Default.Message,
        route = ChildRoute.RoomScreen.route
    ),
    Navitem(
        label = "Call",
        icon = Icons.Default.Call,
        route = ChildRoute.CallScreen.route
    ),
    Navitem(
        label = "Setting",
        icon = Icons.Default.Settings,
        route = ChildRoute.SettingScreen.route
    )
)