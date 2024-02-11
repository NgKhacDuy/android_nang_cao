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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.domain.entity.ProductEntity
import com.example.android_advance.ui.product.ProductViewModel

@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun SearchCard(avatar: Int,name: String,latestMessage:String){
    var poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    Card(
        modifier= Modifier
            .size(width = screenWidth.dp, height = 55.dp)
            .background(color = Color.Transparent)
    ){
        Row(modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start=5.dp)){
                Image(painter=painterResource(id = avatar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(
                        50.dp
                    )
                        .clip(CircleShape))
                Column(verticalArrangement = Arrangement.SpaceBetween,
                    modifier= Modifier
                        .fillMaxHeight()
                        .padding(start = 10.dp)) {
                    Text(text=name,fontSize = 20.sp, fontFamily = poppinsFamily )
                    Text(text=latestMessage, fontFamily = poppinsFamily, color = Color.Gray)
                }
            }

        }
    }
}
@Composable
fun SearchScreenPP() {
    var poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    var searchValue by remember { mutableStateOf("") }
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
                OutlinedTextField(
                    modifier = Modifier
                        .width((screenWidth * 0.4f).dp)
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.search_icon_without_circle), null)
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
                    onValueChange = { searchValue = it },
                    label = { Text("Search", fontFamily = poppinsFamily) },
                    singleLine = true
                )
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(weight = 1f, fill = false)
                            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 20.dp)
                            .background(color = Color.Transparent),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp),) {
                            Text(
                                text = "People",
                                fontFamily = poppinsFamily,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 20.dp)
                                    .align(Alignment.Start)
                            )
                            SearchCard(R.drawable.user_solid, "name", "Today")
                            Divider(
                                color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                    .width((screenWidth / 4).dp)
                                    .align(Alignment.End)
                            )
                            SearchCard(R.drawable.user_solid, "name", "Keep working")
                            Divider(
                                color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                    .width((screenWidth / 4).dp)
                                    .align(Alignment.End)
                            )
                            SearchCard(R.drawable.user_solid, "name", "Be yourself")
                            Divider(
                                color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                    .width((screenWidth / 4).dp)
                                    .align(Alignment.End)
                            )
                            SearchCard(R.drawable.user_solid, "name", "Tomorrow")
                            Divider(
                                color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                    .width((screenWidth / 4).dp)
                                    .align(Alignment.End)
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp),) {
                            Text(
                                text = "Group",
                                fontFamily = poppinsFamily,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 20.dp)
                                    .align(Alignment.Start)
                            )
                            SearchCard(R.drawable.user_solid, "name", "Today, 09:30 AM")
                            Divider(
                                color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                    .width((screenWidth / 4).dp)
                                    .align(Alignment.End)
                            )
                            SearchCard(R.drawable.user_solid, "name", "Today, 09:30 AM")
                            Divider(
                                color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                    .width((screenWidth / 4).dp)
                                    .align(Alignment.End)
                            )
                            SearchCard(R.drawable.user_solid, "name", "Today, 09:30 AM")
                            Divider(
                                color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                    .width((screenWidth / 4).dp)
                                    .align(Alignment.End)
                            )
                            SearchCard(R.drawable.user_solid, "name", "Today, 09:30 AM")
                            Divider(
                                color = Color.LightGray, thickness = 0.7.dp, modifier = Modifier
                                    .width((screenWidth / 4).dp)
                                    .align(Alignment.End)
                            )
                        }
                    }
                    Box(modifier=Modifier.fillMaxWidth())
                    {
                        Row(modifier=Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                            NavBarCard("Messages",R.drawable.message_icon)
                            NavBarCard("Calls",R.drawable.phone_call_icon)
                            NavBarCard("Setting",R.drawable.gear_icon)
                        }
                    }
                }
        }
    }
}

@Preview
@Composable
fun SearchPreview()
{
    SearchScreenPP()
}
