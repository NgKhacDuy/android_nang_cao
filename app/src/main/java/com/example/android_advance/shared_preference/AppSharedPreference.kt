package com.example.android_advance.shared_preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class AppSharedPreference(context: Context) {
    private val mainKeyAlies = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        "preferences",
        mainKeyAlies,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val encryptedEditor = encryptedSharedPreferences.edit()

    private val keyAccessToken = "accessToken"
    private val keyRefreshToken = "refreshToken"

    var accessToken
        get() = encryptedSharedPreferences.getString(keyAccessToken, "").toString()
        set(value) {
            encryptedEditor.putString(keyAccessToken, value)
            encryptedEditor.commit()
        }

    var refreshToken
        get() = encryptedSharedPreferences.getString(keyRefreshToken, "").toString()
        set(value) {
            encryptedEditor.putString(keyRefreshToken, value)
            encryptedEditor.commit()
        }
}