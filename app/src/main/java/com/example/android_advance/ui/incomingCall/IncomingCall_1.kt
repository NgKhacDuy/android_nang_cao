package com.example.android_advance.ui.incomingCall

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
Dành cho view Khi gọi đến 1 người nào đó.Truyền vào user_name và id avatar
 */

@Composable
fun IncomingC_2(name: String,avtId:Int, modifier: Modifier = Modifier) {
    val avtUser = painterResource(id = avtId)
    Box(modifier= modifier
        .background(
            Color(
            69, 188, 204
        )
        )
        .fillMaxHeight()) {

        Text(
            text ="Calling....",
            modifier=modifier.padding(120.dp,100.dp,0.dp,0.dp),
            fontSize = 40.sp,
        )
        Image(
            painter = avtUser,
            contentDescription = null,
            modifier= modifier
                .padding(110.dp, 200.dp, 100.dp, 100.dp)
                .width(200.dp)
                .height(200.dp),

            )
        IncomingC(name =name,
            modifier = Modifier
                .padding(0.dp, 100.dp, 0.dp, 0.dp)
        )


    }
}
@Composable
fun IncomingC(name: String, modifier: Modifier = Modifier) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(150.dp,300.dp,0.dp,0.dp)
    ) {
        Text(
            text = name,
            fontSize = 40.sp,
            lineHeight = 50.sp,
            textAlign = TextAlign.Center,
            color= Color.Black
        )
        Button(
            modifier = modifier
                .clip(CircleShape)
                .width(100.dp)
                .height(100.dp),
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(242, 116, 116))

        ) {
            Icon(painter = painterResource(id = com.example.android_advance.R.drawable.icons8_hang_up_96), contentDescription = "null")
        }

    }

}

