package com.example.android_advance.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AlertDialogComponent(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    positiveText: String,
    negativeText: String,
    iconType: IconType = IconType.NONE
) {
    val icon: ImageVector? = when (iconType) {
        IconType.SUCCESS -> Icons.Filled.CheckCircle
        IconType.ERROR -> Icons.Filled.Error
        IconType.WARNING -> Icons.Filled.Warning
        else -> null
    }

    val iconTint = when (iconType) {
        IconType.SUCCESS -> Color.Green
        IconType.ERROR -> Color.Red
        IconType.WARNING -> Color.Yellow
        else -> if (icon != null) Color.Unspecified else null // Default tint if icon is present, otherwise null
    }

    AlertDialog(
        icon = icon?.let {
            {
                if (iconTint != null) {
                    Icon(it, contentDescription = "Icon for $iconType", tint = iconTint)
                }
            }
        },
        title = {
            Text(text = dialogTitle, textAlign = TextAlign.Center)
        },
        text = {
            Text(text = dialogText, textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(positiveText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(negativeText)
            }
        }
    )
}

enum class IconType {
    SUCCESS,
    ERROR,
    WARNING,
    NONE
}

