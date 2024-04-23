package com.example.android_advance.ui.ListUser.component

import android.util.Log
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.ui.ListUser.ListUserViewModel

@Composable
fun UserItem(
    user: UserDto,
    isOwner: Boolean,
    viewModel: ListUserViewModel,
    listUser: ArrayList<String>
) {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = user.name!!)
                    if (isOwner) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null)
                        }
                    }
                }

                Column {

                    if (user.id != viewModel.userDto.id) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                Icons.Default.PersonRemove,
                                contentDescription = null,
                                tint = Color.Red
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