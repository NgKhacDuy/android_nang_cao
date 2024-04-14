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
    object CallHistoryScreen : Route(Constant.callHistoryScreen)
    object CallGroupScreen : Route(Constant.callGroupScreen)
    object CreateGroupScreen : Route(Constant.createGroupScreen)
    object PollScreen : Route(Constant.pollScreen)
    object IncomingCall_1 : Route(Constant.incomingCall_1)
    object IncomingCall_2 : Route(Constant.incomingCall_2)
    object MessageScreen : Route(Constant.messageScreen)
    object ProfileScreen : Route(Constant.profileScreen)
    object SettingScreen : Route(Constant.settingScreen)
    object VideoCall_1 : Route(Constant.videoCall_1)
    object VideoCall_2 : Route(Constant.videoCall_2)
    object RoomScreen : Route(Constant.roomScreen)
    object CallScreen : Route(Constant.callScreen)
    object VideoScreen : Route(Constant.videoScreen)
    object OptionsMenuChat : Route(Constant.optionsMenu)

    object AccountScreen : Route(Constant.accountScreen)
    object ManageAccountInfoScreen : Route(Constant.manageAccountInfoScreen)
    object ChangePasswordScreen : Route(Constant.changePasswordScreen)
    object ImagePicker : Route(Constant.ImagePicker)
    object SplashScreen : Route(Constant.splashScreen)
    object MenuOption : Route(Constant.menuOption)
    object ListUserInGroup : Route(Constant.listUserInGroup)
    object InviteMemberScreen : Route(Constant.InviteMember)
    object ContactScreen : Route(Constant.contactScreen)


    object listUserGroupScreen : Route(Constant.ListUserInGroup)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}