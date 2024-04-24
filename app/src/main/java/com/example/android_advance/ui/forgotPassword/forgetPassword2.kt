package com.example.android_advance.ui.forgotPassword



import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.navigation.Route


/*
*User will input Otp in this screen
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyOTPScreen(navController: NavController,userId:String,sendOtp:String) {
    val viewModel = hiltViewModel<forgetPasswordViewModel>()
    var otpCode by remember { mutableStateOf("") }

    var serverOtp by remember { mutableStateOf("") }
   Log.d("Sent otp = ",sendOtp)


    Surface(
        modifier = Modifier.fillMaxSize(),
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
                text = "Verify OTP",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "An OTP has been sent to the phone number you provided. Please enter the code below.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val focusManager = LocalFocusManager.current
            val (digit1, setDigit1) = remember { mutableStateOf("") }
            val (digit2, setDigit2) = remember { mutableStateOf("") }
            val (digit3, setDigit3) = remember { mutableStateOf("") }
            val (digit4, setDigit4) = remember { mutableStateOf("") }
            var otpCode=digit1+digit2+digit3+digit4

            val allFieldsFilled = digit1.isNotEmpty() && digit2.isNotEmpty() && digit3.isNotEmpty() && digit4.isNotEmpty()
            LaunchedEffect(digit1, digit2, digit3, digit4) {
                if(allFieldsFilled){
                    focusManager.clearFocus()
                }
                else if (digit1.isNotEmpty()) {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next,
                    )
                }
                else if (digit2.isNotEmpty()) {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next,
                    )
                }
                else if (digit3.isNotEmpty()) {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                OtpTextField(digit1, setDigit1)
                OtpTextField(digit2, setDigit2)
                OtpTextField(digit3, setDigit3)
                OtpTextField(digit4, setDigit4)
            }




            Spacer(modifier = Modifier.height(32.dp))
            VerifyOTPButton(
                navController=navController,
                enabled = allFieldsFilled,
                otpCode=otpCode,serverOtp=sendOtp,userId=userId
            )

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}


@Composable
fun OtpTextField(text: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = { onTextChanged(it.take(1)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next,
        ),
        modifier = Modifier.width(64.dp),
    )
}


@Composable
fun VerifyOTPButton(navController: NavController,enabled: Boolean,otpCode:String,serverOtp:String,userId:String) {
    var otpError by remember { mutableStateOf(false) }
    if (otpError) {
        Text(
            text = "Please enter a valid OTP",
            color = Color.Red,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
    Button(
        onClick = { /* Handle button click */
            Log.d("OptScreen", "Submitted Otp: $otpCode")
            if(serverOtp==otpCode){
                otpError=false
                navController.navigate(Route.ForgetPassword3.withArgs(userId))

            }
            else{
                otpError=true
            }
        },
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Verify OTP")
    }
}