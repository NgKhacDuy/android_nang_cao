package com.example.android_advance.components.ImagePicker

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel()
class ImagePickerViewModel @Inject public constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _hasStoragePermission = mutableStateOf(false)
    val hasStoragePermission: State<Boolean> = _hasStoragePermission

    fun onPermissionsResult(
        acceptedStoragePermission: Boolean,
    ) {
        _hasStoragePermission.value = acceptedStoragePermission
    }
}