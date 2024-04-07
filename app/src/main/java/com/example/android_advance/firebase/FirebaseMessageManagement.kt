package com.example.android_advance.firebase

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.response.FriendResponse
import com.example.android_advance.shared_preference.AppSharedPreference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class FirebaseMessageManagement: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e("NEW_TOKEN", token)
    }
    fun onTokenRefresh() {}
    fun sendUserRoomToServer(roomId: String?,idList:ArrayList<String>) {
        val deviceToken = hashMapOf(
            "roomId" to roomId,
            "idList" to idList,
            "timestamp" to FieldValue.serverTimestamp(),
        )
        Firebase.firestore.collection("roomList").document(roomId!!)
            .set(deviceToken)
    }

    fun sendTokenWithUserId(userId: String){
        //var user=db.getUser().first()
        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if (result != null && result != "") {
                Log.e("FB TOKEN", result)
                val deviceToken = hashMapOf(
                    "userId" to userId,
                    "token" to result,
                    "timestamp" to FieldValue.serverTimestamp(),
                )
                Firebase.firestore.collection("fcmTokens").document(userId)
                    .set(deviceToken)
            }
        }
    }

    fun findUserToken()
    {

    }
}