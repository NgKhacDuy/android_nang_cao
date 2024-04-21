package com.example.android_advance.ui.call_history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android_advance.R


@Composable
fun CallHistoryFavCard() {
    var poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .background(color = Color.Transparent)

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(color = Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_solid),
                contentDescription = null,
                modifier = Modifier.size(
                    40.dp
                )
            )
            Text(text = "name", fontFamily = poppinsFamily)
        }
    }
}

@Composable
fun CallHistoryCard(avatar: Int, name: String, time: String, type: Int) {
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
                Image(
                    painter = painterResource(id = avatar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(
                            50.dp
                        )
                        .clip(CircleShape)
                )
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 10.dp)
                ) {
                    Text(text = name, fontSize = 20.sp, fontFamily = poppinsFamily)
                    Row {
                        Image(
                            painter = painterResource(id = type),
                            contentDescription = null,
                            modifier = Modifier.size(
                                25.dp
                            )
                        )
                        Text(text = time, fontFamily = poppinsFamily, color = Color.Gray)
                    }
                }
            }
            Box {
                Row()
                {
                    Image(painter = painterResource(id = R.drawable.phone),
                        contentDescription = null,
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .size(25.dp)
                            .clickable {
                                println("Button Clicked!")
                            })
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(painter = painterResource(id = R.drawable.video_call),
                        contentDescription = null,
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .size(27.dp)
                            .clickable {
                                println("Button Clicked!")
                            })
                }
            }
        }
    }
}

@Composable
fun NavBarCard(name: String, image: Int) {
    var poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    Card(
        modifier = Modifier
            .background(color = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(color = Color.White)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.size(
                    60.dp
                )
            )
            Text(text = name, fontSize = 15.sp, fontFamily = poppinsFamily)
        }
    }
}

@Composable
fun CallHistoryScreenPP(navController: NavController) {
    var poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    //var searchValue by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.welcome),
                contentScale = ContentScale.FillBounds
            ),
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box() {
                    Image(painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                println("Button Clicked!")
                            })
                }
                Text(
                    text = "Contacts",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontFamily = poppinsFamily
                )
                Box(contentAlignment = Alignment.Center) {
                    Image(painter = painterResource(id = R.drawable.phone_plus),
                        contentDescription = null,
                        modifier = Modifier
                            .size(27.dp)
                            .clickable {
                                println("Button Clicked!")
                            })
                    Image(
                        painter = painterResource(id = R.drawable.gray_circle),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
            /*OutlinedTextField(
                modifier = Modifier
                    .width((screenWidth * 0.4f).dp)
                    .padding(horizontal = 10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                value = searchValue,
                onValueChange = { searchValue = it },
                label = { Text("Search", fontFamily = poppinsFamily) },
                singleLine = true
            )*/
            Box(
                modifier = Modifier
                    .width((screenWidth * 0.4f).dp)
                    .padding(horizontal = 10.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .border(
                        BorderStroke(1.dp, color = Color.Black),
                        shape = RoundedCornerShape(15.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                ) {
                    Text(text = "Favorite", fontFamily = poppinsFamily)
                    Row {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .weight(weight = 1f, fill = true)
                        )
                        {
                            CallHistoryFavCard()
                            CallHistoryFavCard()
                            CallHistoryFavCard()
                            CallHistoryFavCard()
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)
                    )
                    .border(
                        BorderStroke(1.dp, color = Color.Black),
                        shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)
                    )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.gray_rectangle),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        Text(text = "Recent", fontFamily = poppinsFamily, fontSize = 15.sp)
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(weight = 1f, fill = false)
                            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 20.dp)
                            .background(color = Color.Transparent),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CallHistoryCard(
                            R.drawable.user_solid,
                            "name",
                            "Today, 09:30 AM",
                            R.drawable.phone_arrow_down_left
                        )
                        Divider(
                            color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                .width((screenWidth / 4).dp)
                                .align(Alignment.End)
                        )
                        CallHistoryCard(
                            R.drawable.user_solid,
                            "name",
                            "Today, 09:00 AM",
                            R.drawable.phone_arrow_down_left
                        )
                        Divider(
                            color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                .width((screenWidth / 4).dp)
                                .align(Alignment.End)
                        )
                        CallHistoryCard(
                            R.drawable.user_solid,
                            "name",
                            "Yesterday, 07:35 PM",
                            R.drawable.phone_arrow_down_left
                        )
                        Divider(
                            color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                .width((screenWidth / 4).dp)
                                .align(Alignment.End)
                        )
                        CallHistoryCard(
                            R.drawable.user_solid,
                            "name",
                            "Today, 09:00 AM",
                            R.drawable.phone_arrow_down_left
                        )
                        Divider(
                            color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                .width((screenWidth / 4).dp)
                                .align(Alignment.End)
                        )
                        CallHistoryCard(
                            R.drawable.user_solid,
                            "name",
                            "Yesterday, 07:35 PM",
                            R.drawable.phone_arrow_down_left
                        )
                        Divider(
                            color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                .width((screenWidth / 4).dp)
                                .align(Alignment.End)
                        )
                        CallHistoryCard(
                            R.drawable.user_solid,
                            "name",
                            "Yesterday, 07:35 PM",
                            R.drawable.phone_arrow_down_left
                        )
                        Divider(
                            color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                .width((screenWidth / 4).dp)
                                .align(Alignment.End)
                        )
                        CallHistoryCard(
                            R.drawable.user_solid,
                            "name",
                            "Yesterday, 07:35 PM",
                            R.drawable.phone_arrow_down_left
                        )
                        Divider(
                            color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                .width((screenWidth / 4).dp)
                                .align(Alignment.End)
                        )
                        CallHistoryCard(
                            R.drawable.user_solid,
                            "name",
                            "Yesterday, 07:35 PM",
                            R.drawable.phone_arrow_down_left
                        )
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun CallHistoryPreview() {
//    CallHistoryScreenPP()
//}
