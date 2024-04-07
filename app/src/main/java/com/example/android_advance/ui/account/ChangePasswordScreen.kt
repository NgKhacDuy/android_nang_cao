package com.example.android_advance.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dsc.form_builder.TextFieldState
import com.example.android_advance.R
import com.example.android_advance.navigation.Route
import androidx.compose.foundation.lazy.*

@Composable
fun ChangePasswordScreen(navController: NavController)
{
    val configuration = LocalConfiguration.current
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    val accountViewModel = hiltViewModel<AccountScreenViewModel>()
    val userId = accountViewModel.userId
    val formState = remember {
        accountViewModel.passwordFormState
    }
    val passwordState: TextFieldState = formState.getState("password")
    val checkPasswordState: TextFieldState = formState.getState("checkpass")
    fun handleUpdate() {
        if (formState.validate() && passwordState.value==checkPasswordState.value) {
            if (userId != null) {
                accountViewModel.updatePassword(passwordState.value)
                navController.navigate(route = Route.SettingScreen.route)
            }
        }
    }
    Box(modifier = Modifier.fillMaxWidth())
    {
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start = 10.dp))
        {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = null, modifier = Modifier.size(26.dp))
            }

        }

        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Image(painter = painterResource(id = R.drawable.user_solid),
                contentDescription = null,
                //contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(screenWidth.dp / 9)
                    .clip(CircleShape)
            )
            OutlinedTextField(value = passwordState.value,
                onValueChange = { passwordState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done),
                label = { Text(text = "Enter new password") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color(0xFF3D4A7A),
                    unfocusedLabelColor = Color(0xFF3D4A7A),
                    focusedIndicatorColor = Color(0xFFCDD1D0),
                ),
                supportingText = {
                    if (passwordState.hasError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = passwordState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End

                        )
                    }
                }
            )
            OutlinedTextField(value = checkPasswordState.value,
                onValueChange = { checkPasswordState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done),
                label = { Text(text = "Password confirm") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color(0xFF3D4A7A),
                    unfocusedLabelColor = Color(0xFF3D4A7A),
                    focusedIndicatorColor = Color(0xFFCDD1D0),
                ),
                supportingText = {
                    if (checkPasswordState.hasError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = checkPasswordState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End
                        )
                    }
                    if (passwordState.value!=checkPasswordState.value)
                    {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Password here is different than the above password",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End
                        )
                    }
                }
            )
            Button(onClick = { handleUpdate()
                accountViewModel.updateUserSqlite()
                checkPasswordState.change("")
                passwordState.change("")}
            ) {
                Text(text = "Change password")
            }
        }
    }
}