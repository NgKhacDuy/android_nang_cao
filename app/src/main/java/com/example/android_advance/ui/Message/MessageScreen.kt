package com.example.android_advance.ui.Message

import android.util.LayoutDirection
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.CameraEnhance
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.VideoCall
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_advance.R


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MessageScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),

    ) {
        Column(

        ) {
            // First Row with size 2/10
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Row(

                    verticalAlignment = Alignment.CenterVertically,

                ){
                    IconButton(onClick = {
//                navController.popBackStack()
                    }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null,modifier = Modifier.size(40.dp))
                    }

                    Image(painter = painterResource(
                        id = R.drawable.person_avt),
                        contentDescription = null,
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)

                        ,
                        contentScale = ContentScale.Crop)
                    Column(
                        modifier = Modifier.offset(x=25.dp)


                    ) {
                        Text(
                            text = "top",
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,

                            )
                        Text(
                            text = "Active now",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,

                            )
                    }
                }


                Row(
                    modifier = Modifier
                        .offset(x=-(16.dp))
                        .width(100.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    IconButton(onClick = {
//                navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Rounded.Call,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp))
                    }
                    IconButton(onClick = {
//                navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Rounded.Videocam,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp))
                    }



                }






            }





            // Second Row with size 8/10
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .padding(top = 8.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
            ) {
                Column (
                ){
                    MessageRow(message = "hiiiii", isSentByMe = true)
                    MessageRow(message = "hello", isSentByMe = false)
                    MessageRow(message = "how are you ?", isSentByMe = false)
                    MessageRow(message = "good !", isSentByMe = true)


                }


//                Text(
//                    text = "center",
//                    color = Color.Black,
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .padding(16.dp)
//                )
            }

            // Third Row with size 2/10
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = {
//                navController.popBackStack()
                }) {
                    Icon(Icons.Rounded.AttachFile, contentDescription = null,modifier = Modifier.size(40.dp))
                }

                OutlinedTextField(
                    value = "", // Pass your state variable here
                    onValueChange = { /* handle value change */ },
                    placeholder = { Text("Type what you like") },
                    modifier = Modifier
                        .weight(1f) // Takes the remaining available space
                        .padding(start = 16.dp)
                )
                IconButton(onClick = {
//                navController.popBackStack()
                }) {
                    Icon(Icons.Rounded.CameraAlt, contentDescription = null,modifier = Modifier.size(40.dp))
                }
                IconButton(onClick = {
//                navController.popBackStack()
                }) {
                    Icon(Icons.Rounded.Mic, contentDescription = null,modifier = Modifier.size(40.dp))
                }


            }
        }
    }
}

@Composable
fun MessageRow(message: String, isSentByMe: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = if (isSentByMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Row {
            val messageBoxAlignment = if (isSentByMe) Alignment.CenterEnd else Alignment.CenterStart
            val imageAlignment = if (isSentByMe) Alignment.Start else Alignment.End

            if (!isSentByMe) {
                Image(
                    painter = painterResource(id = R.drawable.person_avt),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop,
                )
            }

            Box(
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp * 0.6).dp)
                    .background(
                        if (isSentByMe) Color.Blue else Color.Gray,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(8.dp)
                    .padding(start = 8.dp, end = 8.dp),
                contentAlignment = messageBoxAlignment
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (isSentByMe) {
                Image(
                    painter = painterResource(id = R.drawable.person_avt),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}



//@OptIn(ExperimentalComposeUiApi::class)
//@Preview
//@Composable
//fun HomeScreen(navController: NavHostController) {
//    val navController = rememberNavController()
//    HomeScreenContent()
//}
//
