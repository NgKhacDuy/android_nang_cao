package com.example.android_advance.ui.Message.components

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.utils.common.ConvertDateTime
import com.example.android_advance.utils.common.MessageEnum
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.core.content.ContextCompat.startActivity

@Composable
fun ChatItem(message: messageDto, isSender: Boolean) {
    val context = LocalContext.current
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
                    ).width(LocalConfiguration.current.screenWidthDp.dp / 2)
                    .background(Color(0xFFD0BCFF))
                    .padding(16.dp)
                    .clickable {
                        if (message.content != null && message.content!!.isUrl()) {
                            val uri = Uri.parse(message.content)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            val options = Bundle().apply {
                                putInt("android.intent.extra.FLAG_ACTIVITY_NEW_TASK", Intent.FLAG_ACTIVITY_NEW_TASK)
                                putInt("android.intent.extra.FLAG_ACTIVITY_CLEAR_TOP", Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            }
                            startActivity(context, intent, options)
                        }
                    }
            ) {
                Column(
                    horizontalAlignment = if (isSender) Alignment.End else Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    androidx.compose.material.Text(
                        message.user?.name.toString(),
                        color = Color.Gray.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    message.content?.let { Text(text = it) }
                }
            }
        }
    } else {
        ListImage(message, isSender)
    }
}

fun String.isUrl(): Boolean {
    val regex = Regex("(http://|https://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?")
    return regex.matches(this)
}

@Composable
fun AnnotatedString.Builder.appendWithStyle(text: String, style: SpanStyle) {
    return withStyle(style) { append(text) }
}
