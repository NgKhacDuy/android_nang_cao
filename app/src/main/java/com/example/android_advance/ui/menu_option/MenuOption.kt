package com.example.android_advance.ui.menu_option

import android.annotation.SuppressLint
import android.util.Log
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
import com.example.android_advance.redux.Store
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MenuOption(navController: NavController, isGroup: Boolean, avatar: String) {
    val store = Store.getStore()
    val roomDto = store?.state?.roomDto
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