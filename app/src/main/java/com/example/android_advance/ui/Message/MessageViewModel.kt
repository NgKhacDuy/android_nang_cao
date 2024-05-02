package com.example.android_advance.ui.Message

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.request.MessageRequest
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.redux.Store
import com.example.android_advance.socketio.SocketManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    @ApplicationContext val context: Context, val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private var hasCalledGetRoomForUser = false
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
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        if (!hasCalledGetRoomForUser) {
            getRoomForUser(roomId)
            hasCalledGetRoomForUser = true
        }
        db = DatabaseHelper(context)
    }

    private val _onNewMessage = MutableLiveData<List<messageDto>>(emptyList())
    val onNewMessage: LiveData<List<messageDto>> get() = _onNewMessage
    var selectedImageBase64 =
        mutableStateOf<List<String>>(emptyList())

    suspend fun decodeJson(data: Any): List<messageDto> {
        return coroutineScope.async(Dispatchers.Default) {
            val listType = object : TypeToken<List<messageDto>>() {}.type
            gson.fromJson<List<messageDto>>(data.toString(), listType)
        }.await()
    }

    fun getRoomForUser(roomId: String) {
        try {
            socketManager.connect()
            socketManager.emit("join_room", roomId)
            socketManager.on("message") { args ->
                Log.e("message", "message")
                if (args.isNotEmpty()) {
                    val data = args[0]
                    if (data != null && data.toString().isNotEmpty()) {
                        coroutineScope.launch {
                            _onNewMessage.postValue(decodeJson(data))
                        }
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
                        coroutineScope.launch {
                            _onNewMessage.postValue(decodeJson(data))
                        }
                    }
                } else {
                    _onNewMessage.postValue(emptyList())
                }
            }
        }
    }
}