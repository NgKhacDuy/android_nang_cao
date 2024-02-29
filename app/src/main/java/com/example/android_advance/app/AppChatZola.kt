package com.example.android_advance.app

import android.app.Application
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class AppChatZola : Application() {
    override fun onCreate() {
        super.onCreate()
        val onesignalAppId = "4effd7b4-5ab3-4596-b9db-4ed983632ef1"

        // OneSignal Initialization
        OneSignal.initWithContext(this, onesignalAppId)

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
        }

    }
}