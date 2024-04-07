package com.example.android_advance.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dsc.form_builder.TextFieldState
import com.example.android_advance.R
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.navigation.Route

@Composable
fun ManageAccountInfoScreen(navController: NavController)
{
    val configuration = LocalConfiguration.current
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    val screenHeight = configuration.screenHeightDp
    val accountViewModel = hiltViewModel<AccountScreenViewModel>()
    val userId = accountViewModel.userId
    val currentUser = accountViewModel.currentUser
    val formState = remember{
        accountViewModel.userInfoFormState
    }
    val sdtState: TextFieldState = formState.getState("sdt")
    val nameState: TextFieldState = formState.getState("name")
    if (sdtState.value=="")
        currentUser.phoneNumber?.let { sdtState.change(it) }
    if (nameState.value=="")
        currentUser.name?.let { nameState.change(it) }
    fun handleUpdate() {
        if (formState.validate()) {
            if (userId != null) {
                accountViewModel.updateUserInfo(nameState.value, sdtState.value)
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.TopStart)
    {
        Row()
        {
            androidx.compose.material.IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
            Image(painter = painterResource(id = R.drawable.user_solid),
                contentDescription = null,
                //contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(screenWidth.dp / 9)
                    .clip(CircleShape)
                )
            OutlinedTextField(value = nameState.value,
                onValueChange = { nameState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done),
                label = { Text(text = "Enter your name")},
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color(0xFF3D4A7A),
                    unfocusedLabelColor = Color(0xFF3D4A7A),
                    focusedIndicatorColor = Color(0xFFCDD1D0),
                ),
                supportingText = {
                    if (nameState.hasError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = nameState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End

                        )
                    }
                }
            )
            OutlinedTextField(value = sdtState.value,
                onValueChange = { sdtState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
                label = { Text(text = "Enter your phone number")},
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color(0xFF3D4A7A),
                    unfocusedLabelColor = Color(0xFF3D4A7A),
                    focusedIndicatorColor = Color(0xFFCDD1D0),
                ),
                supportingText = {
                    if (sdtState.hasError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = sdtState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End
                        )
                    }
                }
            )
            OutlinedTextField(value = nameState.value,
                onValueChange = { nameState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done),
                label = { Text(text = "Enter your name")},
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color(0xFF3D4A7A),
                    unfocusedLabelColor = Color(0xFF3D4A7A),
                    focusedIndicatorColor = Color(0xFFCDD1D0),
                ),
                supportingText = {
                    if (nameState.hasError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = nameState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End

                        )
                    }
                }
            )
            Button(onClick = { handleUpdate()
            navController.navigate(route = Route.SettingScreen.route)
            accountViewModel.updateUserSqlite()
            sdtState.change("")
            nameState.change("")}
            ) {
                Text(text = "Update info")
            }
        }
    }
}
