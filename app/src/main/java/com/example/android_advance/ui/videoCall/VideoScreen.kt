package com.example.android_advance.ui.videoCall

import android.Manifest
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.BottomNavigation.ChildRoute
import com.example.android_advance.ui.login.loginViewModel
import com.example.android_advance.utils.common.Constant
import io.agora.agorauikit_android.AgoraConnectionData
import io.agora.agorauikit_android.AgoraSettings
import io.agora.agorauikit_android.AgoraVideoViewer
import io.agora.rtc.IRtcEngineEventHandler


@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoScreen(
    roomName: String,
    navController: NavController
) {
    val viewModel = hiltViewModel<VideoViewModel>()
    viewModel.savedStateHandle.set("roomName", roomName)
    var agoraView: AgoraVideoViewer? = null
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            viewModel.onPermissionsResult(
                acceptedCameraPermission = perms[Manifest.permission.RECORD_AUDIO] == true,
                acceptedAudioPermission = perms[Manifest.permission.CAMERA] == true
            )
        }
    )
    LaunchedEffect(key1 = true) {
        permissionLauncher.launch(
            arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA)
        )
    }
    BackHandler {
        agoraView?.leaveChannel()
    }

//    if (viewModel.hasAudioPermission.value && viewModel.hasCameraPermission.value) {
    if (!viewModel.isLeaveChannel.value) {
        AndroidView(factory = {
            AgoraVideoViewer(
                it,
                connectionData = AgoraConnectionData(
                    appId = Constant.appIdAgora,
                    appToken = viewModel.agoraToken.value
                ),
            ).also {
                try {
                    it.join(
                        roomName,
                        token = viewModel.agoraToken.value
                    )
                    it.style = AgoraVideoViewer.Style.FLOATING
                    it.delegate = null
                    it.agkit.addHandler(object : IRtcEngineEventHandler() {
                        override fun onLeaveChannel(stats: RtcStats?) {
                            super.onLeaveChannel(stats)
                            viewModel.triggerLeaveChannel()

                        }
                    }
                    )
                    agoraView = it

                } catch (e: Exception) {
                    Log.e("VideoScreen", e.toString())
                }
            }
        }, modifier = Modifier.fillMaxSize())
    } else {
        navController.navigate(route = "home")
//
    }


}