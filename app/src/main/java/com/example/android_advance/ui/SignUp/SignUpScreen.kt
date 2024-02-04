package com.example.android_advance.ui.SignUp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.android_advance.navigation.Route


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(navController: NavController) {
    var phoneNum by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passConfirm by remember { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    val keybroadController = LocalSoftwareKeyboardController.current

    fun validation() {
        if (phoneNum.isEmpty() || fullName.isEmpty() || password.isEmpty() || passConfirm.isEmpty()) {
            isError = true
        } else {
            isError = false
            navController.navigate(Route.ProductScreen.withArgs(phoneNum,fullName, password, passConfirm))

            // Implement your logic for successful sign-up and navigation here
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

            Text("Sign Up with email", style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally))
            Text("Get chatting with friends and family today by signing up for our chat app!",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(16.dp, 0.dp, 16.dp, 0.dp))
                    .align(Alignment.CenterHorizontally),
                value = phoneNum,
                onValueChange = { phoneNum = it },
                label = { Text("Phone Number") },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Số điện thoại sai định dạng",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(16.dp, 0.dp, 16.dp, 0.dp))
                    .align(Alignment.CenterHorizontally),
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Không được để trống",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(16.dp, 0.dp, 16.dp, 0.dp))
                    .align(Alignment.CenterHorizontally),
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Không để trống mật khẩu ",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(16.dp, 0.dp, 16.dp, 0.dp))
                    .align(Alignment.CenterHorizontally)
                ,
                value = passConfirm,
                onValueChange = { passConfirm = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Không để trống mật khẩu ",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                }
            )

            Button(
                modifier = Modifier
                    .width((screenWidth * 0.4f).dp)
                    .height(48.dp)
                    ,
                onClick = {
                    validation()
                    keybroadController?.hide()
                }

            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Companion.Medium

                )

            }
        }
    }
}
//@OptIn(ExperimentalComposeUiApi::class)

//@Preview
//@Composable
//fun SignUpScreen() {
//    val navController = rememberNavController()
//    SignUpScreenContent(navController = navController)
//}