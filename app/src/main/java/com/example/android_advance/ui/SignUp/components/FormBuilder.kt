package com.example.android_advance.ui.SignUp.components

import android.content.res.loader.ResourcesProvider
import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.FormField
import ch.benlu.composeform.validators.NotEmptyValidator

class FormBuilder(resourcesProvider: ResourcesProvider) : ch.benlu.composeform.Form() {
    override fun self(): ch.benlu.composeform.Form {
        return this
    }

    @FormField
    val basicForm = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(NotEmptyValidator())
    )
}