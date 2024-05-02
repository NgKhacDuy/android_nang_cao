package com.example.android_advance.ui.Message.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android_advance.components.ViewImg.ExpandedImage
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.utils.common.ConvertDateTime
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun ListImage(message: messageDto, isSender: Boolean) {
    var isSelected = remember { mutableStateOf(false) }
    var indexSelected = remember { mutableIntStateOf(0) }
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
            Column(horizontalAlignment = if (isSender) Alignment.End else Alignment.Start) {
                Text(message.user?.name.toString(), color = Color.Magenta.copy(alpha = 0.9f))
                Spacer(modifier = Modifier.height(4.dp))
                val itemSize: Dp =
                    if (message.image.size > 2)
                        (LocalConfiguration.current.screenWidthDp.dp / 6)
                    else
                        (LocalConfiguration.current.screenWidthDp.dp / 4)
                FlowRow(
                    mainAxisSize = SizeMode.Wrap,
                    mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
                )
                {
                    for (i in message.image) {
                        AsyncImage(
                            model = i.url,
                            contentDescription = null,
                            modifier = Modifier
                                .size(itemSize)
                                .clickable {
                                    isSelected.value = true
                                    indexSelected.value = message.image.indexOf(i)
                                })
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                message.createAt?.let {
                    androidx.compose.material3.Text(
                        text = ConvertDateTime().timeAgo(it),
                        color = Color.Gray.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
    if (isSelected.value) {
        ExpandedImage(message.image[indexSelected.intValue].url!!) {
            isSelected.value = false
        }
    }
}