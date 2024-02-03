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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_advance.R

@Composable
fun ProfileScreen(){
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
                Text(
                    text = "John Doe",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
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

        // User Information
        UserInfo(title = "Display Name", value = "John Doe")
        UserInfo(title = "Email", value = "john.doe@example.com")
        UserInfo(title = "Phone Number", value = "+123456789")
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