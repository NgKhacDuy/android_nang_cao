package com.example.android_advance.ui.Message

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.request.MessageRequest
import com.example.android_advance.model.request.RoomRequest
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.redux.Store
import com.example.android_advance.socketio.SocketManager
import com.example.android_advance.ui.BottomNavigation.ChildRoute
import com.example.android_advance.ui.Group.GroupScreenModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    @ApplicationContext val context: Context, val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    val store = Store.getStore()
    val roomDto = store?.state?.roomDto
    val roomId = roomDto?.id!!
    val partnerName = roomDto?.partner?.name ?: roomDto?.name
    val isGroup = roomDto?.isGroup

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    var db: DatabaseHelper
    val socketManager = SocketManager.getInstance(context)


    init {
        getRoomForUser(roomId)
        db = DatabaseHelper(context)
    }

    private val _onNewMessage = MutableLiveData<List<messageDto>>(emptyList())
    val onNewMessage: LiveData<List<messageDto>> get() = _onNewMessage
    var selectedImageBase64 =
        mutableStateOf<List<String>>(emptyList())

    fun getRoomForUser(roomId: String) {
        try {
            socketManager.connect()
            socketManager.emit("join_room", roomId)
            socketManager.on("message") { args ->
                if (args.isNotEmpty()) {
                    val data = args[0]
                    if (data != null && data.toString().isNotEmpty()) {
                        val listType = object : TypeToken<List<messageDto>>() {}.type
                        val messages: List<messageDto> = gson.fromJson(data.toString(), listType)
                        _onNewMessage.postValue(messages)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", e.message.toString())
        }
    }

    fun sendMessage(content: String, typeMessage: String, image: List<String>) {
        if (content.isNotEmpty()) {
            socketManager.emit(
                "message",
                gson.toJson(db.getUser().first().id?.let {
                    MessageRequest(
                        it,
                        content,
                        roomId,
                        typeMessage,
                        image
                    )
                })
            )
        }
        socketManager.on("message") { args ->
            args.let { d ->
                if (d.isNotEmpty()) {
                    val data = d[0]
                    if (data.toString().isNotEmpty()) {
                        val listType = object : TypeToken<List<messageDto>>() {}.type
                        val messages: List<messageDto> = gson.fromJson(data.toString(), listType)
                        _onNewMessage.postValue(messages)
                    }
                } else {
                    _onNewMessage.postValue(emptyList())
                }
            }
        }
    }
}