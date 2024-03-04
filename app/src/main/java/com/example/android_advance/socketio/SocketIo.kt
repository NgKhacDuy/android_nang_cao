package com.example.android_advance.socketio

import android.content.Context
import android.util.Log
import com.example.android_advance.api.appInterceptor
import com.example.android_advance.shared_preference.AppSharedPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

class SocketManager @Inject constructor(@ApplicationContext private val context: Context) {

    private var socket: Socket? = null
    private val url = "https://android-nang-cao-backend.onrender.com"
    val appSharedPreference = AppSharedPreference(context)
    val appInterceptor = appInterceptor(context)

    fun refreshToken() {
        appInterceptor.refreshTokenFunction()
    }

    fun connect() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val options = IO.Options().apply {
                    // Set the authorization header as a list
                    extraHeaders = mapOf("Authorization" to listOf(appSharedPreference.accessToken))
                }
                socket = IO.socket(url, options)
                socket?.connect()
            } catch (e: ConnectException) {
                Log.e("error socket", e.message.toString())
                e.printStackTrace()
            }
        }
    }

    fun disconnect() {
        socket?.disconnect()
        socket = null
    }

    fun emit(event: String, data: String) {
        GlobalScope.launch(Dispatchers.IO) {
            socket?.emit(event, data)
        }
    }

    fun on(event: String, callback: (args: Array<Any>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            if (event == "Error") {
                refreshToken()
                socket?.connect()
            }
            socket?.on(event) { args ->
                callback(args)
            }
        }
    }
}
