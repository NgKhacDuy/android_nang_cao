package com.example.android_advance.ui.call_history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.model.response.FriendsDto
import com.example.android_advance.ui.search.SearchScreenModel
import kotlinx.coroutines.*

@Composable
fun SearchCard(avatar: Int, name: String, latestMessage: String, friend: ArrayList<FriendsDto>) {
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
                    Text(text = latestMessage, fontFamily = poppinsFamily, color = Color.Gray)
                }
            }
            IconButton(onClick = {

            }) {
                if (friend.isEmpty()) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(24.dp))
                } else {
                    if (friend.first().status == "Accepted") {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(24.dp))

                    } else {
                        Icon(Icons.Filled.HourglassTop, contentDescription = null, modifier = Modifier.size(24.dp))

                    }

                }

            }

        }
    }
}

@Composable
fun SearchScreenPP(navController: NavController) {
    val viewModel = hiltViewModel<SearchScreenModel>()
    var poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    var searchValue by remember { mutableStateOf("") }
    val debounceJob = remember { mutableStateOf<Job?>(null) }
    val searchState = viewModel.searchResults.observeAsState()
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                searchValue = ""
            },
        ) {
            Icon(
                Icons.Default.Clear,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
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
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = null, modifier = Modifier.size(26.dp))
                }
                OutlinedTextField(
                    modifier = Modifier
                        .width((screenWidth * 0.4f).dp)
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon_without_circle),
                            null
                        )
                    },
                    trailingIcon = if (searchValue.isNotBlank()) trailingIconView else null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF3F6F6),
                        unfocusedContainerColor = Color(0xFFF3F6F6),
                        disabledContainerColor = Color(0xFFF3F6F6),
                        focusedBorderColor = Color(0xFFF3F6F6),
                        unfocusedBorderColor = Color(0xFFF3F6F6),
                        focusedLabelColor = Color.Black
                    ),
                    value = searchValue,
                    onValueChange = { newValue ->
                        searchValue = newValue
                        if (newValue.isNotEmpty() && newValue.isNotBlank()) {
                            debounceJob.value?.cancel()
                            debounceJob.value = CoroutineScope(Dispatchers.Main).launch {
                                delay(2000L)
                                viewModel.performSearch(newValue)
                            }
                        }
                    },
                    label = { Text("Search", fontFamily = poppinsFamily) },
                    singleLine = true
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                    ),
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

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "People",
                            fontFamily = poppinsFamily,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .align(Alignment.Start)
                        )
                        searchState.value?.forEach { result ->
                            result.name?.let { SearchCard(R.drawable.user_solid, it, "Today ", result.friends) }
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
