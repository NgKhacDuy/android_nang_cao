package com.example.android_advance.ui.call_history.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.android_advance.R
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.ui.call_history.ContactViewModel

@Composable
fun CallHistoryCard(userDto: UserDto, viewModel: ContactViewModel, navController: NavController) {
    var poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    Card(
        modifier = Modifier
            .size(width = screenWidth.dp, height = 55.dp)
            .background(color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 5.dp)
            ) {
                if (userDto.avatar.isNullOrBlank()) {
                    Image(
                        painter = painterResource(id = R.drawable.user_solid),
                        contentDescription = null,
                        modifier = Modifier
                            .size(
                                50.dp
                            )
                            .clip(CircleShape)
                    )
                } else {
                    AsyncImage(
                        model = userDto.avatar,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(
                                50.dp
                            )
                            .clip(CircleShape)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = userDto.name.toString(),
                        fontSize = 20.sp,
                        fontFamily = poppinsFamily
                    )
                }
            }
            Box {
                Row()
                {
                    Icon(
                        imageVector = Icons.Default.ChatBubble,
                        contentDescription = null,
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .size(25.dp)
                            .clickable {
                                viewModel.navigateToRoom(userDto.id!!, navController)
                            })

                }
            }
        }
    }
}