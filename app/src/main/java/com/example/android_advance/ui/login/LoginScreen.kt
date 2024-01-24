package com.example.android_advance.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android_advance.navigation.Route

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    val keybroadController = LocalSoftwareKeyboardController.current
    fun validation() {
        if (username.isEmpty() || password.isEmpty()) {
            isError = true
        } else {
            isError = false
            navController.navigate(Route.ProductScreen.withArgs(username, password))
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
            OutlinedTextField(
                modifier = Modifier.width((screenWidth * 0.4f).dp),
                value = username,
                onValueChange = { username = it },
                label = { Text("Tên đăng nhập") },
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier.width((screenWidth * 0.4f).dp),
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Nhập đủ tài khoản và mật khẩu",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Button(modifier = Modifier.width((screenWidth * 0.4f).dp),
                onClick = {
                    validation()
                    keybroadController?.hide()
                }) {
                Text(text = "Đăng nhập")
            }
        }
    }
}