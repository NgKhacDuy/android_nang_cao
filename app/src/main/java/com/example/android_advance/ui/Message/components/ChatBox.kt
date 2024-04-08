package com.example.android_advance.ui.Message.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_advance.components.ImagePicker.ImagePicker
import com.example.android_advance.ui.Message.MessageViewModel
import com.example.android_advance.utils.common.MessageEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBox(
    onSendChatClickListener: (String, String, List<String>) -> Unit,
    modifier: Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    val viewModel = hiltViewModel<MessageViewModel>()
    Row(modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        IconButton(
            onClick = {
                showImagePickerDialog = true
            },
            modifier = Modifier
                .clip(CircleShape)
                .background(color = Color(0xFFD0BCFF))
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Image,
                contentDescription = "Send_Picture",
                modifier = Modifier.fillMaxSize().padding(8.dp)
            )
        }
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = "Type something")
            }
        )
        IconButton(
            onClick = {
                val msg = chatBoxValue.text
                if (msg.isBlank()) return@IconButton
                onSendChatClickListener(chatBoxValue.text, MessageEnum.RAW.type, arrayListOf())
                chatBoxValue = TextFieldValue("")
            },
            modifier = Modifier
                .clip(CircleShape)
                .background(color = Color(0xFFD0BCFF))
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Send",
                modifier = Modifier.fillMaxSize().padding(8.dp)
            )
        }
    }

    if (showImagePickerDialog) {
        var selectedImageBase64: ArrayList<String> = arrayListOf()
        ImagePicker(
            onDismiss = {
                showImagePickerDialog = false
            },
            context,
            onImagesSelected = { base64Image ->
                base64Image.forEach {
                    selectedImageBase64.add(it)
                }

                onSendChatClickListener("Hình ảnh", MessageEnum.IMAGE.type, selectedImageBase64.toList())
            }) // Call ImagePicker here when the dialog should be shown
    }
}