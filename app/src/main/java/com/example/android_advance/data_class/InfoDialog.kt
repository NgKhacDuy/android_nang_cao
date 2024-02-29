package com.example.android_advance.data_class

import com.example.android_advance.ui.components.IconType

data class InfoDialog(
    val onDismissRequest: () -> Unit,
    val onConfirmation: () -> Unit,
    val dialogTitle: String,
    val dialogText: String,
    val positiveText: String,
    val negativeText: String,
    val iconType: IconType = IconType.NONE
)