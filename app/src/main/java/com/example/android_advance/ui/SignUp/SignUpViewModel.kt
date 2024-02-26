package com.example.android_advance.ui.SignUp

import androidx.lifecycle.ViewModel
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators

class SignUpViewModel : ViewModel() {
    val formState = FormState(
        fields = listOf(
            TextFieldState(
                name = "sdt",
                validators = listOf(
                    Validators.Min(10, "Số điện thoại phải tối thiểu 10 số")
                )
            ),
            TextFieldState(
                name = "name",
                validators = listOf(
                    Validators.Required()
                )
            ), TextFieldState(
                name = "password",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "password_confirm",
                validators = listOf(
                    Validators.Custom(
                        "Mật khẩu xác nhận không khớp"
                    ) { confirmation -> passwordMatch(confirmation) },
                    Validators.Required("Không được để trống mật khẩu xác nhận")
                )
            )
        )
    )

    private fun passwordMatch(confirmationPass: Any): Boolean {
        return confirmationPass == formState.fields.find { it.name == "password" }?.value.toString()
    }
}