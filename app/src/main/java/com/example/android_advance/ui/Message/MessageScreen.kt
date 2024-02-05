package com.example.android_advance.ui.Message

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),


        ) {
            // First Row with size 2/10
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = painterResource(
                    id = R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .padding(8.dp)
                    ,
                    contentScale = ContentScale.Crop)

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
                Row(
                    modifier = Modifier.offset(x=100.dp).width(100.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Image(painter = painterResource(
                        id = R.drawable.icons8_call_96),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)

                        ,
                        contentScale = ContentScale.Crop)

                    Image(painter = painterResource(
                        id = R.drawable.icons8_video_call_96),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)

                        ,
                        contentScale = ContentScale.Crop)
                }






            }

            // Second Row with size 6/10
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .background(Color.Blue) ,// Replace with your desired background color
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "center",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }

            // Third Row with size 2/10
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f)
                    .background(Color.Green) ,// Replace with your desired background color
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "bottom",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
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