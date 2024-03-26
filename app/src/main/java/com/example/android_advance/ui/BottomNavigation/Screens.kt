package com.example.android_advance.ui.BottomNavigation

import com.example.android_advance.utils.common.Constant

sealed class ChildRoute(val route: String) {
    object MessageScreen : ChildRoute(Constant.messageScreen)
    object CallScreen : ChildRoute(Constant.callScreen)
    object SettingScreen : ChildRoute(Constant.settingScreen)
    object SearchScreen : ChildRoute(Constant.searchScreen)
    object RoomScreen : ChildRoute(Constant.roomScreen)

    object CreateGroup : ChildRoute(Constant.createGroupScreen)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}