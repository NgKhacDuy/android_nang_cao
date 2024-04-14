package com.example.android_advance.components.ImagePicker


import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_advance.ui.videoCall.VideoViewModel
import com.example.android_advance.utils.common.GetPath
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException

@Composable
fun ImagePicker(
    onDismiss: () -> Unit,
    context: Context,
    onImagesSelected: (List<String>) -> Unit,
    isSelectMulti: Boolean
) {
    val viewModel = hiltViewModel<ImagePickerViewModel>()
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            viewModel.onPermissionsResult(
                acceptedStoragePermission = perms[Manifest.permission.READ_EXTERNAL_STORAGE] == true,
            )
        }
    )

    fun getRealPathFromUri(context: Context, uri: Uri): String? {
        val projection =
            arrayOf(MediaStore.Images.Media.DATA) // Replace with appropriate data column for your media type
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        return cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                it.getString(columnIndex)
            } else {
                null
            }
        }
    }

    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    var selectedImageBase64 by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uri ->
            if (!uri.isEmpty()) {
                val pathFile = getRealPathFromUri(context, uri[0]) ?: ""
                val listImg: ArrayList<String> = arrayListOf()
                listImg.add(pathFile)
                onImagesSelected(listImg)
            }
            onDismiss()
        }
    )

    val mutiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            selectedImageUris = uris
            selectedImageUris.forEach(
                action = {
                    val inputStream = context.contentResolver.openInputStream(it)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    inputStream?.use { input ->
                        byteArrayOutputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                    val byteArray = byteArrayOutputStream.toByteArray()

                    // Encode the image data as Base64
                    val base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)
                    selectedImageBase64 = selectedImageBase64 + base64Image

                }
            )
            onImagesSelected(selectedImageBase64) // Invoke the callback with the selected images' Base64 strings
            onDismiss()
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
        if (isSelectMulti) {
            mutiplePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

    }
}



