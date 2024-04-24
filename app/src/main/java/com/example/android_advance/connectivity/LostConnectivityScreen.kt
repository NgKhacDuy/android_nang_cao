package com.example.android_advance.connectivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LostConnectivityScreen()
{
    val configuration = LocalConfiguration.current
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    val screenHeight = configuration.screenHeightDp
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White))
    {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
            IndeterminateCircularIndicator(screenWidth)
            Text(text = "No internet connection", fontSize = 20.sp)
            Text(text = "Please wait!", fontSize = 20.sp)
        }
    }
}
@Composable
fun IndeterminateCircularIndicator(screenWidth: Int) {
    var loading by remember { mutableStateOf(true) }

    if (!loading) return

    Box(modifier = Modifier.height((screenWidth/10).dp)) {
        CircularProgressIndicator(
            modifier = Modifier.width((screenWidth/10).dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
@Preview
fun pp()
{
    LostConnectivityScreen()
}