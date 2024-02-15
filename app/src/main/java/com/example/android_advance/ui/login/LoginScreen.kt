package com.example.android_advance.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android_advance.ui.login.components.GradientButtonNoRipple

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    val keybroadController = LocalSoftwareKeyboardController.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val gradientColor = listOf(Color(0xFF020204), Color(0xFF4156a6))
    fun validation() {
        if (username.isEmpty() || password.isEmpty()) {
            isError = true
        } else {
            isError = false

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = null)
            }
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login to Zola", modifier = Modifier
                        .padding(top = 60.dp), fontWeight = FontWeight.Bold, fontSize = 18.sp,
                    color = Color(0xFF3D4A7A)
                )
                Text(
                    text = "Welcome back! Sign in using your social account to continue us",
                    modifier = Modifier
                        .padding(top = 20.dp),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF797C7B)
                )
                val configuration = LocalConfiguration.current
                val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
                val screenHeight = configuration.screenHeightDp
                TextField(
                    modifier = Modifier
                        .width((screenWidth * 0.4f).dp)
                        .padding(top = 76.dp),
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Your Phone Number", color = Color(0xFF3D4A7A)) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedLabelColor = Color(0xFF3D4A7A),
                        unfocusedLabelColor = Color(0xFF3D4A7A),
                        focusedIndicatorColor = Color(0xFFCDD1D0),
                    )
                )
                TextField(
                    modifier = Modifier
                        .width((screenWidth * 0.4f).dp)
                        .padding(top = 24.dp)
                        .background(color = Color.White),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color(0xFF3D4A7A)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedLabelColor = Color(0xFF3D4A7A),
                        unfocusedLabelColor = Color(0xFF3D4A7A),
                        focusedIndicatorColor = Color(0xFFCDD1D0)
                    )
                )
                if (isError) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        text = "Nhập đủ tài khoản và mật khẩu",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                GradientButtonNoRipple(
                    text = "Login",
                    modifier = Modifier
                        .width((screenWidth * 0.4f).dp)
                        .padding(top = (screenHeight * 0.25f).dp)
                        .height(48.dp)
                        .background(
                            brush = Brush.linearGradient(colors = gradientColor),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable(
                            indication = null, // Assign null to disable the ripple effect
                            interactionSource = interactionSource
                        ) {
                            validation()
                            keybroadController?.hide()
                        },
                )

                Text(
                    text = "Forgot Password?",
                    color = Color(0xFF3D4A7A),
                    modifier = Modifier
                        .padding(
                            top = 16.dp
                        )
                        .clickable {
//                        TODO add navigation to forgot password
                        }
                )
            }

        }
    }
}