package com.example.android_advance.components.ImagePicker


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import com.example.android_advance.utils.common.GetPath
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException

@Composable
fun ImagePicker(onDismiss: () -> Unit, context: Context, onImagesSelected: (List<String>) -> Unit) {
    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    var selectedImageBase64 by remember {
        mutableStateOf<List<String>>(emptyList())
    }

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
        mutiplePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
}



