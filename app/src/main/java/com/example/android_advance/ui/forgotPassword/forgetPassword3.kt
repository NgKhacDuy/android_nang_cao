package com.example.android_advance.ui.forgotPassword

import com.example.android_advance.R

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.components.AlertDialogComponent

/*
*User will input there new password in this screen.
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(navController: NavController,userId:String) {
    var newPassword by remember { mutableStateOf("") }
    var reEnterPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var reEnterPasswordError by remember { mutableStateOf(false) }
    val imagePainter = painterResource(id = R.drawable.button_auth)
    val viewModel = hiltViewModel<forgetPasswordViewModel>()
    var changePasswordSucess by remember { mutableStateOf(false) }

    Log.d("screen3",userId)
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),

        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "Reset Password",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "New Password",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(alignment = Alignment.Start),

                )
            TextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text(text = "Enter New Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            if (passwordError) {
                Text(
                    text = "Password must be at least 6 characters",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Text(
                text = "Re-enter Password",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(alignment = Alignment.Start)
            )
            TextField(
                value = reEnterPassword,
                onValueChange = { reEnterPassword = it },
                label = { Text(text = "Re-enter New Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = reEnterPasswordError,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            if (reEnterPasswordError) {
                Text(
                    text = "Passwords do not match",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            SubmitButton(
                onClick = {
                    passwordError = newPassword.length < 6
                    reEnterPasswordError = reEnterPassword != newPassword
                    if(!passwordError&&!reEnterPasswordError){
                        //Do some logic here
                        Log.d("PasswordScreen", "Submitted Password: $newPassword")
                        viewModel.resetPassword(newPassword,userId) { result ->
                            // Perform actions with the userId value
                            if (result) {
                                changePasswordSucess=true
                                Log.d("test", "Change password Successs.")

                                //navController.navigate(Route.LoginScreen.route)
                            } else {
                                Log.d("test", "Change password Fail.")
                            }
                        }
                    }
                },
                passwordError = passwordError,
                reEnterPasswordError = reEnterPasswordError
            )
            Spacer(modifier = Modifier.height(64.dp))
            if(changePasswordSucess)
                ResetPasswordDialog(navController)
        }
    }
}

@Composable
fun SubmitButton(onClick: () -> Unit,passwordError: Boolean,reEnterPasswordError: Boolean) {
    val imagePainter = painterResource(id = R.drawable.button_auth)  // Assuming submit_button_bg.png is your image resource

    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()

        )

        Text(
            text = "Submit",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }

}
@Composable
fun ResetPasswordDialog(navController: NavController) {
    val showDialog = remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Reset Password") },
            text = { Text(text = "Reset Password Sucessful") },
            confirmButton = {
                Button(onClick = { showDialog.value = false
                navController.navigate(Route.LoginScreen.route)}) {
                    Text(text = "Okay")
                }
            }
        )
    }
}

