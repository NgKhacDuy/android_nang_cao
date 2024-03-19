package com.example.android_advance.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import com.example.android_advance.R
import androidx.lifecycle.asLiveData
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.Home.HomeScreenViewModel


@Composable
fun ProfileScreen(){
    val viewModel = hiltViewModel<ProfileScreenViewModel>()
    val userLiveData = viewModel.getFriendInfo()
//    val userState: State<UserDto?> = userLiveData.observeAsState(initial = null)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // User Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomImage(R.drawable.user , "User", size = 100.dp)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
//                when (val user = userState.value) {
//                    null -> {
//                        // Show loading indicator or handle error
//                    }
//                    else -> {
//                        user.name?.let {
//                            Text(
//                                text = it,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 24.sp
//                            )
//                        }
//                    }
//                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomIcon(Icons.Default.Chat, "Chat", size = 24.dp, onClick = { /* Handle chat click */ })
                    CustomIcon(Icons.Default.VideoCall, "Video Call", size = 24.dp, onClick = { /* Handle video call click */ })
                    CustomIcon(Icons.Default.Call, "Call", size = 24.dp, onClick = { /* Handle call click */ })
                }
            }
        }
//        when (val user = userState.value) {
//            null -> {
//                // Show loading indicator or handle error
//            }
//            else -> {
//                user.name?.let { UserInfo(title = "Display Name", value = it) }
//                user.phoneNumber?.let { UserInfo(title = "Phone Number", value = it) }
//            }
//        }
    }
}

@Composable
fun CustomIcon(icon: ImageVector, contentDescription: String, size: Dp, onClick: (() -> Unit)? = null) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable { onClick?.invoke() }
    )
}

@Composable
fun CustomImage(imageResId: Int, contentDescription: String, size: Dp, onClick: (() -> Unit)? = null) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable { onClick?.invoke() }
    )
}

@Composable
fun UserInfo(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun ProfileScreenPR()
{
    ProfileScreen()
}