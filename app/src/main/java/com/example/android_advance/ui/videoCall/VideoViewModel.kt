package com.example.android_advance.ui.videoCall

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.model.request.SigninRequest
import com.example.android_advance.model.response.AgoraTokenDto
import com.example.android_advance.model.response.SigninResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_advance.agora.media.RtcTokenBuilder2
import com.example.android_advance.utils.common.Constant

@HiltViewModel()
class VideoViewModel @Inject public constructor(
    @ApplicationContext private val context: Context, val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val roomName = savedStateHandle.get<String>("roomName") ?: ""

    private val _hasAudioPermission = mutableStateOf(false)
    val hasAudioPermission: State<Boolean> = _hasAudioPermission

    private val _hasCameraPermission = mutableStateOf(false)
    val hasCameraPermission: State<Boolean> = _hasCameraPermission

    private val _agoraToken = mutableStateOf("")
    val agoraToken: State<String> = _agoraToken

    init {
        getToken()
    }

    fun onPermissionsResult(
        acceptedAudioPermission: Boolean,
        acceptedCameraPermission: Boolean
    ) {
        _hasAudioPermission.value = acceptedAudioPermission
        _hasCameraPermission.value = acceptedCameraPermission
    }

    fun getToken() {
        val tokenBuilder = RtcTokenBuilder2()
        val timeStamp = (System.currentTimeMillis() / 1000 + 60).toInt()
        val result = tokenBuilder.buildTokenWithUid(
            Constant.appIdAgora,
            Constant.agoraCert,
            roomName,
            0,
            RtcTokenBuilder2.Role.ROLE_PUBLISHER, timeStamp, timeStamp
        )
        _agoraToken.value = result
    }
}