package com.example.android_advance.ui.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.android_advance.components.ImagePicker.ImagePicker
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.Home.HomeScreenViewModel
import com.example.android_advance.utils.common.MessageEnum
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {
    val settingViewModel = hiltViewModel<SettingScreenViewModel>()
    val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
    val userState = settingViewModel.onNewUserInfo.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var showImagePickerDialog by remember { mutableStateOf(false) }
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
            when (val user = userState.value) {
                null -> {
                    // Show loading indicator or handle error
                }

                else -> {
                    AsyncImage(model = user.avatar,
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(8.dp)
                            .clickable {
                                showImagePickerDialog = true
                            })
//                    Icon(
//                        imageVector = Icons.Default.Person,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(50.dp)
//                            .clip(CircleShape)
//                            .background(MaterialTheme.colorScheme.primary)
//                            .padding(8.dp)
//                            .clickable {
//                                showImagePickerDialog = true
//                            }
//                    )
                    Spacer(modifier = Modifier.width(16.dp))
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
        SettingItem(
            icon = Icons.Default.AccountCircle,
            title = "Account",
            onClick = {
                navController.navigate(route = Route.AccountScreen.route)
            }
        )
        SettingItem(
            icon = Icons.Default.Chat,
            title = "Chat",
            onClick = {
                navController.navigate(Route.ContactScreen.route)
            }
        )
        SettingItem(
            icon = Icons.Default.Notifications,
            title = "Notifications",
            onClick = {}
        )
        SettingItem(
            icon = Icons.Default.Help,
            title = "Help",
            onClick = {}
        )
        SettingItem(
            icon = Icons.Default.Logout,
            title = "Log out",
            onClick = {
                coroutineScope.launch {
                    launch {
                        settingViewModel.signOut { callback ->
                            if (callback) {
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

                    }
                }

            }
        )
    }
    if (showImagePickerDialog) {
        var selectedImageBase64: ArrayList<String> = arrayListOf()
        ImagePicker(
            onDismiss = {
                showImagePickerDialog = false
            },
            context,
            onImagesSelected = { base64Image ->
                base64Image.forEach {
                    selectedImageBase64.add(it)
                }
                settingViewModel.uploadImg(selectedImageBase64)

            },
            isSelectMulti = false,
        ) // Call ImagePicker here when the dialog should be shown
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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