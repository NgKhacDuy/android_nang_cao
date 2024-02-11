package com.example.android_advance.ui.Group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_advance.R

@Composable
fun CreateGroupScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        //Back button
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.clickable { /* Handle back button click */ }
        )

        // Title
        Text(
            text = "Create Group",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .padding(start = 48.dp),
            textAlign = TextAlign.Center
        )

        // Group Description
        Text(
            text = "Group Description",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        BasicTextField(
            value = "Enter group description...",
            onValueChange = {},
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, CircleShape)
                .padding(16.dp)
        )

        // Group Type Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GroupTypeButton(icon = Icons.Default.GroupWork, text = "Group Work", selected = true)
            GroupTypeButton(icon = Icons.Default.Send, text = "Team Relationship", selected = false)
        }

        // Group Admin
        Text(
            text = "Group Admin",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Admin Image
            Image(
                painter = painterResource(id = R.drawable.user), // Replace with actual image
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Admin Name
            Text(
                text = "Admin Name",
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        // Invited Members
        Text(
            text = "Invited Members",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Add Member Button
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { /* Handle add member click */ }
                    .background(Color.White)
                    .padding(8.dp)
            )

            // Invited Members List
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                items(3) { index ->
                    Image(
                        painter = painterResource(id = R.drawable.user), // Replace with actual image
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(8.dp)
                    )
                }
            }
        }

        // Create Group Button
        Button(
            onClick = { /* Handle create group click */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Create Group", color = Color.White)
        }
    }
}

@Composable
fun GroupTypeButton(icon: ImageVector, text: String, selected: Boolean) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(if (selected) Color.Blue else Color.Gray)
            .clickable { /* Handle button click */ }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.White)
            Text(text = text, color = Color.White)
        }
    }
}
