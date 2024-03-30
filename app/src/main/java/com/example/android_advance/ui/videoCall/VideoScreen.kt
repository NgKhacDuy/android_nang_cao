package com.example.android_advance.ui.videoCall

import android.Manifest
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.android_advance.utils.common.Constant
import io.agora.agorauikit_android.AgoraConnectionData
import io.agora.agorauikit_android.AgoraSettings
import io.agora.agorauikit_android.AgoraVideoViewer


@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoScreen(
    roomName: String,
    onNavigateUp: () -> Unit,
    viewModel: VideoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
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
        Log.e("PERMISSION AUDIO", viewModel.hasAudioPermission.value.toString())
        Log.e("PERMISSION CAMERA", viewModel.hasCameraPermission.value.toString())
    }
    BackHandler {
        agoraView?.leaveChannel()
        onNavigateUp()
    }

//    if (viewModel.hasAudioPermission.value && viewModel.hasCameraPermission.value) {
    AndroidView(factory = {
        AgoraVideoViewer(
            it,
            connectionData = AgoraConnectionData(
                appId = Constant.appIdAgora,
                appToken = "007eJxTYJD58X8j1+XDHHN2Tlx59HRDfDFv1bMluvw8ty5fypK9dMhMgcHUzCzZLNXC3DwxydIkyczSMtUsBQgsU4yMDRINjUyaPrOnNQQyMig/+MHMyACBID4HQ1V+TmJyYk4OAwMAZbEi/Q=="
            )
        ).also {
            try {
                it.join(
                    roomName,
                    token = "007eJxTYJD58X8j1+XDHHN2Tlx59HRDfDFv1bMluvw8ty5fypK9dMhMgcHUzCzZLNXC3DwxydIkyczSMtUsBQgsU4yMDRINjUyaPrOnNQQyMig/+MHMyACBID4HQ1V+TmJyYk4OAwMAZbEi/Q=="
                )
                it.style = AgoraVideoViewer.Style.FLOATING
                it.delegate = null
                agoraView = it
            } catch (e: Exception) {
                Log.e("VideoScreen", e.toString())
            }
        }
    }, modifier = Modifier.fillMaxSize())
//    }


}