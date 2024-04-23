package com.example.android_advance.ui.ListUser.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android_advance.R
import com.example.android_advance.model.response.SearchResponse
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.ui.ListUser.ListUserViewModel

@Composable
fun SearchItem(
    user: SearchResponse,
    viewModel: ListUserViewModel
) {
    val isAdded = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors().copy(Color.White),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserImg(user.avatar ?: "")
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = user.name!!)
                Column {
                    if (isAdded.value){
                        IconButton(onClick = {
                            viewModel.removeFriendId(user.id ?: "")
                            isAdded.value = false
                        }) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            viewModel.addFriendId(user.id ?: "")
                            isAdded.value = true
                        }) {
                            Icon(
                                Icons.Default.PersonAdd,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserImg(img: String) {
    val configuration = LocalConfiguration.current
    if (img.isEmpty()) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(configuration.screenWidthDp.dp / 7)
                .clip(
                    RoundedCornerShape(corner = CornerSize(16.dp))
                )
        )
    } else {
        AsyncImage(
            model = img,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(configuration.screenWidthDp.dp / 7)
                .clip(
                    RoundedCornerShape(corner = CornerSize(16.dp))
                )
        )
    }

}