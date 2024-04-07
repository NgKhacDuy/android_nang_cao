package com.example.android_advance.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(navController: NavController)
{
    val viewModel = hiltViewModel<AccountScreenViewModel>()
    val accountInfo = viewModel.getUserInfo()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        contentAlignment = Alignment.TopStart,) {
        Column(modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = null, modifier = Modifier.size(26.dp))
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.user_solid),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(
                            140.dp
                        )
                        .clip(CircleShape)
                        .clickable {
                            println("Button Clicked!")
                        }
                )
                accountInfo.value?.name?.let { Text(text = it, fontSize = 20.sp) }
                accountInfo.value?.phoneNumber?.let { Text(text = it, fontSize = 20.sp) }
                Column {
                    AccountItem(icon = Icons.Default.ManageAccounts, title = "Change account info") {
                        navController.navigate(Route.ManageAccountInfoScreen.route)
                    }
                    AccountItem(icon = Icons.Default.Password, title = "Change account password") {
                        navController.navigate(Route.ChangePasswordScreen.route)
                    }
//                    AccountItem(icon = Icons.Default.ContactPhone, title = "change phone number") {
//
//                    }
//
//                    AccountItem(icon = Icons.Default.Password, title = "change password") {
//
//                    }
//                    Button(onClick = { /*TODO*/ },
//                        colors = ButtonDefaults.buttonColors(Color.Transparent),
//                        modifier = Modifier.fillMaxWidth()) {
//                        Text(text = "change username", color = Color.Black, fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
//                    }
//
//                    Button(onClick = { /*TODO*/ },
//                        colors = ButtonDefaults.buttonColors(Color.Transparent),
//                        modifier = Modifier.fillMaxWidth()) {
//                        Text(text = "change phone number", color = Color.Black, fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
//                    }
//                    Button(onClick = { /*TODO*/ },
//                        colors = ButtonDefaults.buttonColors(Color.Transparent),
//                        modifier = Modifier.fillMaxWidth()) {
//                        Text(text = "change password", color = Color.Black, fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
//                    }
                }
            }
        }
    }
}

@Composable
fun AccountItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp)
    }
}

//@Preview
//@Composable
//fun AccountScreenPP()
//{
//    AccountScreen()
//}