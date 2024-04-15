package com.example.android_advance.ui.Group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.PhoneDisabled
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_advance.R

@Composable
fun CallGroupScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Background call group
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "Meeting with John Doe",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Function Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FunctionButton(icon = Icons.Default.MicOff, onClick = { /* Handle mic button click */ })
                FunctionButton(icon = Icons.Default.VolumeOff, onClick = { /* Handle volume button click */ })
                FunctionButton(icon = Icons.Default.VideocamOff, onClick = { /* Handle video button click */ })
                FunctionButton(icon = Icons.Default.Chat, onClick = { /* Handle chat button click */ })
                FunctionButton(icon = Icons.Default.PhoneDisabled, onClick = { /* Handle end call button click */ })
            }

            // Participants List
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(5) { index ->
                    CallGroupParticipantItem(index = index)
                }
            }
        }
    }
}

@Composable
fun FunctionButton(icon: ImageVector, onClick: () -> Unit) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier
            .size(17.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onClick.invoke() }
            .padding(8.dp)
    )
}

@Composable
fun CallGroupParticipantItem(index: Int) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Participant Image
        Image(
            painter = painterResource(id = R.drawable.user), // Replace with actual image resource
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Participant Name
        Text(
            text = "Participant $index",
            fontSize = 16.sp,
            color = Color.White
        )
    }
}
