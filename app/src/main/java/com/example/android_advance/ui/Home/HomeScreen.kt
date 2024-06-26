package com.example.android_advance.ui.Home


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.android_advance.R
import com.example.android_advance.model.response.roomDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.redux.Store
import com.example.android_advance.ui.BottomNavigation.ChildRoute
import com.example.android_advance.utils.common.ConvertDateTime
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.max

data class User(
    val avatar: String, // Resource ID for the user's avatar
    val name: String? = "",
    val lastMessage: String,
    val lastActive: String,
    val messageCount: Int,
    val idRoom: String,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val roomState = viewModel.onNewRoom.observeAsState()
    val userState = viewModel.onNewUser.observeAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing.value,
        onRefresh = { viewModel.swipe() }
    )
    val convertDateTime: ConvertDateTime = ConvertDateTime()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.25f)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF020204), Color(0xFF4156a6))
                        )
                    )
                    .height((screenHeight * 0.3f).dp)
            ) {
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
                        when (userState.value?.avatar.isNullOrEmpty() || userState.value?.avatar!!.endsWith(".jpg")){
                            true -> {
//                                Log.e("home avatar self is jpg", "${userState.value?.avatar}")
//                                Image(
//                                    painter = painterResource(R.drawable.user),
//                                    contentDescription = null,
//                                    modifier = Modifier.fillMaxSize(), // Make the image fill the Box
//                                    contentScale = ContentScale.Crop
//                                )
                                val selfAvatar = Store.getStore()?.state?.userDto?.avatar
                                AsyncImage(
                                    model = selfAvatar,
                                    contentDescription = "avatar",
                                    modifier = Modifier

                                        .size(35.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            false -> {
                                val user = viewModel.getUserFromSqlite()

                                AsyncImage(
                                    model = user.avatar,
                                    contentDescription = "avatar",
                                    modifier = Modifier

                                        .size(35.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
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
                    if (!roomState.value.isNullOrEmpty()) {
                        roomState.value?.let { it ->
                            items(it.size) {
                                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                val currentDate = sdf.format(Date())
                                val avatar =
                                    if (roomState.value!![it].partner!!.avatar.isNullOrBlank()) "image" else roomState.value!![it].partner!!.avatar!!
                                User(
                                    avatar,
                                    if (roomState.value!![it].isGroup == true) roomState.value!![it].name else roomState.value!![it].partner?.name,
                                    roomState.value?.get(it)?.lastMessage?.content
                                        ?: "Cuộc hội thoại đã được tạo",
                                    convertDateTime.timeAgo(
                                        roomState.value!![it].lastMessage?.createAt ?: currentDate
                                    ),
                                    R.drawable.user,
                                    roomState.value!![it].id!!
                                )
                                    .let { it2 ->
                                        UserRow(
                                            it2, navController, it2.idRoom,
                                            roomState.value!![it].partner?.name ?: it2.name!!,
                                            roomState.value!![it].isGroup!!,
                                            viewModel,
                                            roomState.value!![it]
                                        )

                                    }
                            }
                        }
                    } else {
                        item {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Bạn hiện tại không có cuộc trò chuyện nào cả\nHãy kết bạn và bắt đầu nhắn tin ngay nào",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(16.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Có gì đó sai sai ?\nHãy ấn nút bên dưới để reload lại nào",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(16.dp),
                                    textAlign = TextAlign.Center,
                                )
                                IconButton(onClick = { viewModel.getRoomForUser() }) {
                                    Icon(
                                        Icons.Rounded.Refresh,
                                        contentDescription = "Refresh",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = viewModel.isRefreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun UserRow(
    user: User,
    navController: NavController,
    idRoom: String,
    partnerName: String,
    isGroup: Boolean,
    viewModel: HomeScreenViewModel,
    roomDto: roomDto
) {
    Log.e("avatar", user.avatar)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(start = 4.dp, end = 4.dp)
            .clickable {
                viewModel.storeRoomDto(roomDto)
                navController.navigate(
                    Route.MessageScreen.route
                )
            }
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Black,
                ),
//                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                shape = RoundedCornerShape(40.dp)

            ), // Adjust padding as needed
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(start = 40.dp)
        ) {
            if (user.avatar == "image" || user.avatar.endsWith(".jpg")){
                val testAvatar = user.avatar.toString()
                Log.e("test avatar", testAvatar)
                if (testAvatar.endsWith(".jpg")){
                    AsyncImage(
                        model = testAvatar,
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                else {
                    Image(
                    painter = painterResource(R.drawable.user_solid),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                 )
                }
            }


            else {
                if(user.avatar.isNotEmpty() && !user.avatar.endsWith(".jpg")){

                    Image(
                        painter = painterResource(R.drawable.user_solid),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                val displayMessage = if (user.lastMessage.length > 15) {
                    "${user.lastMessage.take(15)}..."
                } else {
                    user.lastMessage
                }

                val nameRoom = if (user.name!!.length > 15) {
                    "${user.name.take(15)}..."
                } else {
                    user.name
                }
                Text(
                    text = nameRoom,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.widthIn(max = 150.dp),
                    maxLines = 1
                )
                Text(
                    text = displayMessage,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
            }

        }

        Row(
            modifier = Modifier.padding(end = 20.dp)
        ) {
            Text(
                text = user.lastActive,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.widthIn(min = 100.dp)
            )

        }
    }
}


