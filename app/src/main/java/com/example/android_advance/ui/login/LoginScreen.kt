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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dsc.form_builder.TextFieldState
import com.example.android_advance.ui.components.AlertDialogComponent
import com.example.android_advance.ui.components.CenteredProgressIndicator
import com.example.android_advance.ui.login.components.GradientButtonNoRipple

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val keybroadController = LocalSoftwareKeyboardController.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val gradientColor = listOf(Color(0xFF020204), Color(0xFF4156a6))
    val viewModel = hiltViewModel<loginViewModel>()
    val formState = remember {
        viewModel.formState
    }
    val sdtState: TextFieldState = formState.getState("sdt")
    val passwordState: TextFieldState = formState.getState("password")

    fun handleLogin() {
        if (formState.validate()) {
            viewModel.signIn(sdtState.value, passwordState.value, navController)
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
                .padding(horizontal = 20.dp)
                .wrapContentHeight()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(
                    rememberScrollState()
                ),
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
                    value = sdtState.value,
                    onValueChange = { sdtState.change(it) },
                    label = { Text("Your Phone Number", color = Color(0xFF3D4A7A)) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedLabelColor = Color(0xFF3D4A7A),
                        unfocusedLabelColor = Color(0xFF3D4A7A),
                        focusedIndicatorColor = Color(0xFFCDD1D0),
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    supportingText = {
                        if (sdtState.hasError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Vui lòng nhập số điện thoại",
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End

                            )
                        }
                    }
                )
                TextField(
                    modifier = Modifier
                        .width((screenWidth * 0.4f).dp)
                        .padding(top = 24.dp)
                        .background(color = Color.White),
                    value = passwordState.value,
                    onValueChange = { passwordState.change(it) },
                    label = { Text("Password", color = Color(0xFF3D4A7A)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedLabelColor = Color(0xFF3D4A7A),
                        unfocusedLabelColor = Color(0xFF3D4A7A),
                        focusedIndicatorColor = Color(0xFFCDD1D0)
                    ),
                    supportingText = {
                        if (passwordState.hasError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Vui lòng nhập mật khẩu",
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.End

                            )
                        }
                    }
                )


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
                            keybroadController?.hide()
                            handleLogin()
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

    if (viewModel.isLoading.value) {
        CenteredProgressIndicator()
    }
    if (viewModel.isShowDialog.value) {
        AlertDialogComponent(
            onDismissRequest = viewModel.infoDialog.value.onDismissRequest,
            onConfirmation = viewModel.infoDialog.value.onConfirmation,
            dialogTitle = viewModel.infoDialog.value.dialogTitle,
            dialogText = viewModel.infoDialog.value.dialogText,
            positiveText = viewModel.infoDialog.value.positiveText,
            negativeText = viewModel.infoDialog.value.negativeText
        )
    }
}