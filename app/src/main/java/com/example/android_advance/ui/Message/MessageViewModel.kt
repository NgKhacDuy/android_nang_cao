package com.example.android_advance.ui.Message

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.request.MessageRequest
import com.example.android_advance.model.request.RoomRequest
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.socketio.SocketManager
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
    val roomId = savedStateHandle.get<String>("idRoom") ?: ""
    val partnerName = savedStateHandle.get<String>("namePartner") ?: ""

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    var db: DatabaseHelper
    val socketManager = SocketManager.getInstance(context)


    init {
        getRoomForUser(roomId)
        db = DatabaseHelper(context)
    }

    private val _onNewMessage = MutableLiveData<List<messageDto>>()
    val onNewMessage: LiveData<List<messageDto>> get() = _onNewMessage

    fun sendMessage(content: String) {
        if (content.isNotEmpty()) {
            socketManager.emit(
                "message",
                gson.toJson(db.getUser().first().id?.let { MessageRequest(it, content, roomId) })
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
                }
            }
        }
    }

//    fun getRoomForUser(roomId: String) {
//        try {
//            Log.e("RoomID", roomId)
//            socketManager.connect()
//            socketManager.emit("join_room", roomId)
//            socketManager.on("message") { args ->
//                args.let { d ->
//                    if (d.isNotEmpty()) {
//                        val data = d[0]
//                        if (data.toString().isNotEmpty()) {
//                            val listType = object : TypeToken<List<messageDto>>() {}.type
//                            val messages: List<messageDto> = gson.fromJson(data.toString(), listType)
//                            _onNewMessage.postValue(messages)
//                        }
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            Log.e("EXCEPTION", e.message.toString())
//        }
//    }

    fun getRoomForUser(roomId: String) {
        try {
            Log.e("RoomID", roomId)
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
}