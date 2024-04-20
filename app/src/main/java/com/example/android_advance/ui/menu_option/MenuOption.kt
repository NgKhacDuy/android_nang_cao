package com.example.android_advance.ui.menu_option

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.VideoCall
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuOption(navController: NavController, isGroup: Boolean, avatar: String) {
    Column {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
        }
        
        IconButton(onClick = { }) {
            Icon(
                Icons.Rounded.VideoCall,
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
        }

    }
}