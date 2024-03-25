package com.example.android_advance.ui.videoCall

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
    Dành cho video call gọi đến người khác.
 */
@Composable
fun VideoCall_2(name: String,avtId:Int, modifier: Modifier = Modifier) {
    val avtUser = painterResource(id = avtId)
    Box(modifier= modifier
        .background(
            Color(
                69, 188, 204
            )
        )
        .fillMaxHeight()) {

        Text(
            text ="Video Call....",
            modifier=modifier.padding(100.dp,100.dp,0.dp,0.dp),
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
        VideoCall(name =name,
            modifier = Modifier
                .padding(0.dp, 100.dp, 0.dp, 0.dp)
        )


    }
}
@Composable
fun VideoCall(name: String, modifier: Modifier = Modifier) {
    var isMicButtonPressed by remember { mutableStateOf(false) }

    // Button with "crossed-out" effect
    val micIcon: Painter = if (isMicButtonPressed) {
        painterResource(id = com.example.android_advance.R.drawable.icons8_mic_96___cancle)
    } else {
        painterResource(id = com.example.android_advance.R.drawable.icons8_mic_96)
    }
    var isCamButtonPressed by remember { mutableStateOf(false) }

    // Button with "crossed-out" effect
    val camIcon: Painter = if (isCamButtonPressed) {
        painterResource(id = com.example.android_advance.R.drawable.icons8_video_call_96___cancle)
    } else {
        painterResource(id = com.example.android_advance.R.drawable.icons8_video_call_96)
    }
    var isSwitchCamClick by remember { mutableStateOf(false) }

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
    }

    Row (
        modifier=modifier.padding(70.dp,300.dp,0.dp,0.dp)
    ){
        Button(
            modifier = modifier
                .clip(CircleShape)
                .width(80.dp)
                .height(80.dp),
            onClick = { isCamButtonPressed = !isCamButtonPressed},
            colors = ButtonDefaults.buttonColors(containerColor =if (!isCamButtonPressed) Color(230, 237, 237) else Color(194, 188, 188))

        ) {
            Icon(painter = camIcon, contentDescription = "null")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            modifier = modifier
                .clip(CircleShape)
                .width(80.dp)
                .height(80.dp)
            ,
            onClick = { isMicButtonPressed = !isMicButtonPressed},
            colors = ButtonDefaults.buttonColors(containerColor = if (!isMicButtonPressed) Color(230, 237, 237) else Color(194, 188, 188))

        ) {
            Icon(painter = micIcon, contentDescription = "null")

        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            modifier = modifier
                .clip(CircleShape)
                .width(80.dp)
                .height(80.dp),
            onClick = { isSwitchCamClick = !isSwitchCamClick },
            colors = ButtonDefaults.buttonColors(containerColor = if (!isSwitchCamClick) Color(230, 237, 237) else Color(194, 188, 188))

        ) {
            Icon(painter = painterResource(id = com.example.android_advance.R.drawable.icons8_switch_camera_96), contentDescription = "null")
        }
    }
    Button(
        modifier = modifier.padding(160.dp,500.dp,0.dp,0.dp)
            .clip(CircleShape)
            .width(80.dp)
            .height(80.dp),
        onClick = { },
        colors = ButtonDefaults.buttonColors(containerColor = Color(242, 116, 116))

    ) {
        Icon(painter = painterResource(id = com.example.android_advance.R.drawable.icons8_hang_up_96), contentDescription = "null")
    }
}