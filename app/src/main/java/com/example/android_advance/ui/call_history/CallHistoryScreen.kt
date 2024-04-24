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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.ui.call_history.components.CallHistoryCard
import com.example.android_advance.ui.call_history.components.CallHistoryFavCard


@Composable
fun CallHistoryScreenPP(navController: NavController) {
    var poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    val viewModel = hiltViewModel<ContactViewModel>()
    val listContact = viewModel.onNewContact.observeAsState()
    val listFavorite = viewModel.onNewFavorite.observeAsState()
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
                .wrapContentWidth(),
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
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .border(
                        BorderStroke(1.dp, color = Color.Black),
                        shape = RoundedCornerShape(15.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Favorite", fontFamily = poppinsFamily)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    {
                        listContact.value?.let {
                            items(it.size ?: 0) { index ->
                                val user = it.get(index)
                                CallHistoryFavCard(user)
                            }
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
                    LazyColumn(
                        modifier = Modifier
                            .weight(weight = 1f, fill = false)
                            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 20.dp)
                            .background(color = Color.Transparent),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        listContact.value.let {
                            items(it?.size ?: 0) { index ->
                                val user = it?.get(index)
                                CallHistoryCard(user!!)
                                Divider(
                                    color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                        .width((screenWidth / 4).dp)
                                        .align(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
