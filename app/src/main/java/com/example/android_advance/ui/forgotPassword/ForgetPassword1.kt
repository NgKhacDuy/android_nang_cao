package com.example.android_advance.ui.forgotPassword

import com.example.android_advance.R

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.BottomNavigation.ChildRoute
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
*User will input phone number in this screen
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterNumberScreen(navController: NavController) {
    val viewModel = hiltViewModel<forgetPasswordViewModel>()
    var phoneNumber by remember { mutableStateOf("") }
    var phoneNumberError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ){
            androidx.compose.material3.IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }

        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(64.dp)) // Adjust the height of the spacer for more space at the top
            Text(
                text = "Enter your Phone Number",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Please enter the phone number of the account that you want to renew password.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            PhoneNumberInputField(
                phoneNumber = phoneNumber,
                onPhoneNumberChange = { newPhoneNumber ->
                    phoneNumber = newPhoneNumber.take(10) // Limiting to 10 characters
                    phoneNumberError = newPhoneNumber.length < 10
                },
                phoneNumberError = phoneNumberError
            )
            if (phoneNumberError) {
                Text(
                    text = "Phone number must be 10 digits",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            SubmitButton(
                enabled = phoneNumber.length == 10 ,// Enable button only if length is 10
                onClick = { submittedPhoneNumber ->
                    Log.d("ForgotPasswordScreen", "Submitted phone number: $phoneNumber")

                    //viewModel.performPhoneNumberExistenceCheck2(phoneNumber)
                    //Log.d("temporay", viewModel.temporay)
                    viewModel.checkPhoneNumberExistence2(phoneNumber) { userId ->
                        // Perform actions with the userId value
                        if (userId.isNotEmpty()) {
                            Log.d("test", "User ID: $userId")
                            var sendOtp=""
                            viewModel.generateOtp(phoneNumber) { otpgenCode ->
                                // Perform actions with the userId value
                                if (otpgenCode.isNotEmpty()) {
                                    Log.d("Otp code ", "Otp: $otpgenCode")
                                    sendOtp = otpgenCode
                                    navController.navigate(Route.ForgetPassword2.withArgs(userId,sendOtp))
                                } else {
                                    Log.d("test", "error.")
                                }
                            }

                        } else {
                            Log.d("test", "User ID not found.")
                        }
                    }

                }
            )
            Spacer(modifier = Modifier.height(64.dp)) // Add additional space after the button
        }
    }
}

@Composable
fun PhoneNumberInputField(phoneNumber: String, onPhoneNumberChange: (String) -> Unit, phoneNumberError: Boolean) {
    val textFieldShape = MaterialTheme.shapes.small // Set the border radius
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.icons8_vietnam_48), // Assuming ic_vietnam_flag.svg is your vector resource
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 1.dp)
        )
        Box(
            modifier = Modifier
                .padding(end = 3.dp)
                .size(40.dp)
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+84",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
        TextField(
            value = phoneNumber,
            onValueChange = { onPhoneNumberChange(it.filter { char -> char.isDigit() }) },
            label = { Text(text = "Phone Number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            isError = phoneNumberError,
            shape = textFieldShape,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Composable
fun SubmitButton(enabled:Boolean,onClick:(String)->Unit) {
    val imagePainter = painterResource(id = R.drawable.button_auth)  // Assuming submit_button_bg.png is your image resource

    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clickable(enabled) {
                onClick("phone number")
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
            ,alpha=if(enabled)1f else 0.5f
        )

        Text(
            text = "Submit",
            color =if(enabled) Color.White else Color.Black,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
