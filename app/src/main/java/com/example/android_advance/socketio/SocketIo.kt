package com.example.android_advance.socketio

import android.content.Context
import android.util.Log
import com.example.android_advance.api.appInterceptor
import com.example.android_advance.shared_preference.AppSharedPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

class SocketManager @Inject constructor(@ApplicationContext private val context: Context) {

    val appSharedPreference = AppSharedPreference(context)
    val appInterceptor = appInterceptor(context)

    //    private val url = "https://android-nang-cao-backend.onrender.com"
    private val url = "http://192.168.100.2:3000"

    private var options = IO.Options().apply {
        extraHeaders = mapOf("Authorization" to listOf(appSharedPreference.accessToken))
    }
    private val socket: Socket = IO.socket(url, options)

    companion object {
        @Volatile
        private var instance: SocketManager? = null

        fun getInstance(context: Context): SocketManager =
            instance ?: synchronized(this) {
                instance ?: SocketManager(context).also { instance = it }
            }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun connect() {
        GlobalScope.launch(Dispatchers.IO) {
            try {

                socket.connect()
            } catch (e: ConnectException) {
                Log.e("error socket", e.message.toString())
                e.printStackTrace()
            }
        }
    }

    fun disconnect() {
        socket.disconnect()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun emit(event: String, data: String) {
        GlobalScope.launch(Dispatchers.IO) {
            socket.emit(event, data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun on(event: String, callback: (args: Array<Any>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            if (event == "Error") {
                socket.connect()
            }
            socket.on(event) { args ->
                callback(args)
            }
        }
    }
}