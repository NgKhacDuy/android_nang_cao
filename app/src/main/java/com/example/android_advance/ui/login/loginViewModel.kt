package com.example.android_advance.ui.login

import androidx.lifecycle.ViewModel
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators

class loginViewModel : ViewModel() {
    val formState = FormState(
        fields = listOf(
            TextFieldState(
                name = "sdt",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "password",
                validators = listOf(
                    Validators.Required()
                )
            ),
        )
    )
}