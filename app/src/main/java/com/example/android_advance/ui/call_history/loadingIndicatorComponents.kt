package com.example.android_advance.ui.call_history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun CenteredProgressIndicator(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.Black.copy(alpha = 0.5f),
                    shape = RectangleShape
                )
                .clickable(enabled = false) { },
            contentAlignment = Alignment.Center
        ) {
            // Show CircularProgressIndicator when isLoading is true
            CircularProgressIndicator()
        }
    }
}