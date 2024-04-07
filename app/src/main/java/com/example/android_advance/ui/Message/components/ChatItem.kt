package com.example.android_advance.ui.Message.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.utils.common.ConvertDateTime
import com.example.android_advance.utils.common.MessageEnum

@Composable
fun ChatItem(message: messageDto, isSender: Boolean) {
    val convertDateTime: ConvertDateTime = ConvertDateTime()
    if (message.type == MessageEnum.RAW.type) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(if (isSender) Alignment.End else Alignment.Start)
                    .clip(
                        RoundedCornerShape(
                            topStart = 48f,
                            topEnd = 48f,
                            bottomStart = if (isSender) 48f else 0f,
                            bottomEnd = if (isSender) 0f else 48f
                        )
                    )
                    .background(Color(0xFFD0BCFF))
                    .padding(16.dp)
            ) {
                message.content?.let { Text(text = it) }
            }
        }
    } else {
        ListImage(message, isSender)
    }
}