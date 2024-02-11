package com.example.android_advance.ui.Group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_advance.R


@Composable
fun CreatePollScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cancel Button
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { /* Handle cancel button click */ }
            )

            // Title
            Text(
                text = "Create Poll",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            // Empty space for alignment
            Box(modifier = Modifier.width(48.dp))
        }

        // What would you like to work
        Text(
            text = "What would you like to work",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Radio Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PollOptionRadioButton("Though")
            PollOptionRadioButton("UI/UX priority")
            PollOptionRadioButton("Design")
        }

        // Voted members
        Text(
            text = "Voted Members",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Voted Members List
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
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

        // Create Poll Button
        Button(
            onClick = { /* Handle create poll click */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Create Poll", color = Color.White)
        }
    }
}

@Composable
fun PollOptionRadioButton(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { /* Handle radio button click */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = false, // Replace with actual logic
            onClick = { /* Handle radio button click */ },
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 16.sp, color = Color.Black)
    }
}
