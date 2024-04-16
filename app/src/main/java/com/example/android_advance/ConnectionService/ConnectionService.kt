package com.example.android_advance.ConnectionService

import android.telecom.Connection
import android.telecom.ConnectionRequest
import android.telecom.ConnectionService
import android.telecom.PhoneAccountHandle
import android.util.Log

class MyConnectionService : ConnectionService() {
    override fun onCreateIncomingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        val connection: Connection = object : Connection() {
            override fun onAnswer() {
                Log.e("ConnectionService", "answer")
            }

            override fun onReject() {
                Log.e("ConnectionService", "reject")
            }

            override fun onDisconnect() {
                Log.e("ConnectionService", "disconnect")
            }
        }
        return connection

    }
}