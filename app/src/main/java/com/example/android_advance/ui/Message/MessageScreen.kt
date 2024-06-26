package com.example.android_advance.ui.Message

import ChatScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.redux.RemoveRoomDto
import com.example.android_advance.voice_to_text.VoiceToTextParser


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MessageScreen(
//    idRoom: String,
    navController: NavController,
//    namePartner: String,
//    isGroup: Boolean,
//    avatar: String
    voiceToTextParser: VoiceToTextParser
) {
    val viewModel = hiltViewModel<MessageViewModel>()
    val messageState = viewModel.onNewMessage.observeAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding(),

        ) {
        Column(

        ) {
            when (messageState.value == null) {
                true -> {
                    messageState.value?.let { it ->
                        ChatScreen(
                            model = it,
                            onSendChatClickListener = { content, type, image ->
                                viewModel.sendMessage(content, type, image)
                            },
                            modifier = Modifier,
                            onClickBack = {
                                viewModel.socketManager.disconnect()
                                navController.popBackStack()
                            },
                            viewModel.db,
                            viewModel.partnerName!!,
                            navController,
                            viewModel.roomId,
                            viewModel.isGroup!!,
                            viewModel.roomDto?.partner?.avatar ?: "",
                            viewModel.context,
                            voiceToTextParser
                        )
                    }
                }

                false -> {
                    messageState.value?.let { it ->
                        ChatScreen(
                            model = it,
                            onSendChatClickListener = { content, type, image ->
                                viewModel.sendMessage(content, type, image)
                            },
                            modifier = Modifier,
                            onClickBack = {
                                viewModel.socketManager.disconnect()
                                navController.popBackStack()
                            },
                            viewModel.db,
                            viewModel.partnerName!!,
                            navController,
                            viewModel.roomId,
                            viewModel.isGroup!!,
                            viewModel.roomDto?.partner?.avatar ?: "",
                            viewModel.context,
                            voiceToTextParser
                        )
                    }
                }

            }
        }
    }
}
