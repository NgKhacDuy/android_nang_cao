package com.example.android_advance.ui.Home


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.model.response.roomDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class User(
    val avatar: Int, // Resource ID for the user's avatar
    val name: String? = "",
    val lastMessage: String,
    val lastActive: String,
    val messageCount: Int
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val roomState = viewModel.onNewRoom.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.25f)
        ) {
            Box(


            ) {
                // Content for the background image
                Image(
                    painter = painterResource(id = R.drawable.welcome),
                    contentDescription = null,
                    contentScale = ContentScale.Crop

                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = {
//                        navController.navigate(Route.SearchScreen.route)
                        viewModel.getRoomForUser()
                    }) {
                        Icon(
                            Icons.Rounded.Search,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }

                    // Text overlay on top of the image
                    Text(
                        text = "Home",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .align(Alignment.TopCenter)
//                            .offset(y = 40.dp)
                    )

                    // Second image
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(Color.White) // Set the background color
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.person_avt),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(), // Make the image fill the Box
                            contentScale = ContentScale.Crop
                        )
                    }


                }


            }

            // Spacer with a specific height to create a separation between the first and second box
            Spacer(modifier = Modifier.height(50.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f) // Adjust the height of the second box
                .align(Alignment.BottomStart)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, shape = RoundedCornerShape(30.dp))
            ) {
                LazyColumn {
                    roomState.value?.let { it ->
                        items(it.size) {
                            roomState.value!![it].lastMessage?.content?.let { it1 ->
                                User(
                                    R.drawable.person_avt,
                                    roomState.value!![it].name,
                                    it1,
                                    timeAgo(roomState.value!![it].lastMessage?.createAt.toString()),
                                    R.drawable.user
                                )
                            }?.let { it2 -> UserRow(it2) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserRow(user: User) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(start = 4.dp, end = 4.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Black,
                ),
//                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                shape = RoundedCornerShape(40.dp)

            ), // Adjust padding as needed
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .offset(x = (-20).dp)
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(user.avatar),
                contentDescription = null,
                modifier = Modifier

                    .size(35.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                val displayMessage = if (user.lastMessage.length > 13) {
                    "${user.lastMessage.take(13)}..."
                } else {
                    user.lastMessage
                }

                val nameRoom = if (user.name!!.length > 10) {
                    "${user.name.take(13)}..."
                } else {
                    user.name
                }
                Text(text = nameRoom, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = displayMessage, fontSize = 16.sp, fontWeight = FontWeight.Normal)
            }

        }
        // Three columns with Text elements

//        Column(
////            modifier = Modifier.offset(x=(-30).dp)
//        ){
//                Text(text = user.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                Text(text = user.lastMessage, fontSize = 16.sp, fontWeight = FontWeight.Normal)
//        }
        Column(
//            modifier = Modifier.offset(x=(-20).dp)
        ) {


            Text(text = user.lastActive, fontSize = 16.sp, fontWeight = FontWeight.Normal)
            Image(
                painter = painterResource(
                    id = user.messageCount
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.End)
            )

        }
    }
}

fun timeAgo(dtStr: String, locale: Locale = Locale("vi", "VN")): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    formatter.timeZone = TimeZone.getTimeZone("UTC")

    val date = formatter.parse(dtStr)
    val now = Date()

    val delta = now.time - date.time

    // Vietnamese unit translations (modify as needed)
    val units = arrayOf(
        Pair("năm", 31536000000L),
        Pair("tháng", 2592000000L),
        Pair("tuần", 604800000L),
        Pair("ngày", 86400000L),
        Pair("giờ", 3600000L),
        Pair("phút", 60000L),
        Pair("giây", 1000L)
    )

    // Find the most suitable unit
    for (unit in units) {
        val threshold = unit.second
        if (delta >= threshold) {
            val value = (delta / threshold).toInt()
            val unitStr = unit.first + (if (value > 1) "" else "")
            return "$value $unitStr trước" // Customize formatting for Vietnamese
        }
    }

    return "vừa mới" // "vừa mới" for "just now" in Vietnamese
}