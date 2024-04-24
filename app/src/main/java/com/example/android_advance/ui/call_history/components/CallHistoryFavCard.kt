package com.example.android_advance.ui.call_history.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android_advance.R
import com.example.android_advance.model.response.UserDto

@Composable
fun CallHistoryFavCard(userDto: UserDto) {
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
            if (userDto.avatar.isNullOrBlank()) {
                Image(
                    painter = painterResource(id = R.drawable.user_solid),
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            40.dp
                        )
                        .clip(CircleShape)
                )
            } else {
                AsyncImage(
                    model = userDto.avatar, contentDescription = null,
                    modifier = Modifier
                        .size(
                            40.dp
                        )
                        .clip(CircleShape)
                )
            }
            Text(text = userDto.name.toString(), fontFamily = poppinsFamily)
        }
    }
}