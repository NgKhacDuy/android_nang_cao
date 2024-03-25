package com.example.android_advance.ui.Home


import android.content.Context
import androidx.compose.foundation.*
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import com.example.android_advance.R
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.BottomNavigation.ChildRoute
import com.example.android_advance.utils.common.ConvertDateTime

data class User(
    val avatar: Int, // Resource ID for the user's avatar
    val name: String? = "",
    val lastMessage: String,
    val lastActive: String,
    val messageCount: Int,
    val idRoom: String
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val roomState = viewModel.onNewRoom.observeAsState()
    val convertDateTime: ConvertDateTime = ConvertDateTime()
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
                        navController.navigate(Route.SearchScreen.route)
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.4f))
                ) {
                    IconButton(
                        onClick = { /* Handle account button click */ },
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center)
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Account",
                            tint = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.4f))
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(Route.CreateGroupScreen.route)
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center)
                    ) {
                        Icon(
                            Icons.Filled.Group,
                            contentDescription = "Group",
                            tint = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.4f))
                ) {
                    IconButton(
                        onClick = { /* Handle call button click */ },
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center)
                    ) {
                        Icon(
                            Icons.Filled.Call,
                            contentDescription = "Call",
                            tint = Color.White
                        )
                    }
                }
            }
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
                                    roomState.value!![it].partner?.name,
                                    it1,
                                    convertDateTime.timeAgo(roomState.value!![it].lastMessage?.createAt.toString()),
                                    R.drawable.user,
                                    roomState.value!![it].id!!
                                )
                            }?.let { it2 ->
                                roomState.value!![it].partner?.name?.let { it1 ->
                                    UserRow(
                                        it2, navController, it2.idRoom,
                                        it1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserRow(user: User, navController: NavController, idRoom: String, partnerName: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(start = 4.dp, end = 4.dp)
            .clickable {
                navController.navigate(ChildRoute.MessageScreen.withArgs(user.idRoom, partnerName))
            }
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

                val nameRoom = if (user.name!!.length > 15) {
                    "${user.name.take(15)}..."
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


