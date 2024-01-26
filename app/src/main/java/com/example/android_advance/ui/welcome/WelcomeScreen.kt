package com.example.android_advance.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_advance.R
import com.example.android_advance.ui.welcome.component.ButtonSignUp

@Composable
fun WelcomeScreen() {
    fun onButtonClick() {}
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "Image Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 85.dp, start = 26.dp, end = 26.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment =
        Alignment.CenterHorizontally
    ) {
        Text(text = "Connect friends easily & quickly", color = Color.White, fontSize = 68.sp)
        Text(
            modifier = Modifier.padding(top = 40.dp, bottom = 80.dp),
            text = "Our app is perfect way to stay connected with friends and family.",
            color = Color(0xFF8d91a2),
            fontSize = 16.sp
        )
        ButtonSignUp(text = "Sign up with phone number", onClick = { onButtonClick() })
        Row(modifier = Modifier.padding(top = 46.dp)) {
            Text(text = "Existing account?", color = Color.White, fontSize = 16.sp)
            Text(
                text = " Log in",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.clickable(enabled = true) { }
            )
        }
    }

}