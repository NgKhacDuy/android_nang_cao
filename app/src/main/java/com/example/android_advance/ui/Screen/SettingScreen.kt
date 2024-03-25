package com.example.android_advance.ui.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.ui.Home.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {
    val viewModel = hiltViewModel<SettingScreenViewModel>()
    val userLiveData = viewModel.getUserInfo()
    val userState: State<UserDto?> = userLiveData.observeAsState(initial = null)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Settings",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        // Handle actions if needed
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "User")
                }
            }
        )

        // User Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            when (val user = userState.value) {
                null -> {
                    // Show loading indicator or handle error
                }
                else -> {
                    user.name?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        // Settings List
        SettingItem(icon = Icons.Default.AccountCircle, title = "Account",navController)
        SettingItem(icon = Icons.Default.Chat, title = "Chat",navController)
        SettingItem(icon = Icons.Default.Notifications, title = "Notifications",navController)
        SettingItem(icon = Icons.Default.Help, title = "Help",navController)
        SettingItem(icon = Icons.Default.PersonAdd, title = "Invite Friend",navController)
        SettingItem(icon = Icons.Default.Logout, title = "Log Out",navController)
    }
}

@Composable
fun SettingItem(icon: ImageVector, title: String,navController: NavController) {
    val settingViewModel = hiltViewModel<SettingScreenViewModel>()
    val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (title == "Log Out") {
                    settingViewModel.deleteToken()
                    settingViewModel.deleteSqlite()
                    homeScreenViewModel.disconnectSocket()
                        .also {
                            navController.navigate(route = "auth") {
                                popUpTo("home") {
                                    inclusive = true
                                }
                            }
                        }
                }
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp)
    }
}