package com.example.android_advance.ui.ListUser

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.Home.HomeScreenViewModel
import com.example.android_advance.ui.ListUser.component.UserItem
import com.example.android_advance.ui.Message.MessageViewModel
import com.example.android_advance.ui.login.components.GradientButtonNoRipple

data class User(val name: String, val avatar: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUserGroup(navController: NavController) {
    val viewModel = hiltViewModel<ListUserViewModel>()
    var searchValue by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val gradientColor = listOf(Color(0xFF1b2f83), Color(0xFF25c7ca))
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                "Members",
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .weight(5f)
                    .padding(start = 34.dp)
            )
            Spacer(Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        GradientButtonNoRipple(
            text = "Invite Member",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(40.dp)
                .background(
                    brush = Brush.linearGradient(colors = gradientColor),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(
                    indication = null, // Assign null to disable the ripple effect
                    interactionSource = interactionSource,
                ) {
                    navController.navigate(Route.InviteMemberScreen.route)
                },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "List Members",
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
//        androidx.compose.material3.OutlinedTextField(
//            value = searchValue,
//            onValueChange = { newValue ->
//                searchValue = newValue
//            },
//            label = { androidx.compose.material3.Text("Search") },
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            singleLine = true,
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = Color.Blue,
//                unfocusedBorderColor = Color.Gray,
//            ),
//        )

        LazyColumn {
            items(viewModel.roomDto?.user?.size ?: 0) { it ->
                val user = viewModel.roomDto!!.user!!
                val listId = viewModel.roomDto.listId
                UserItem(
                    user = user[it],
                    isOwner = user[it].id == listId.first(),
                    viewModel,
                    listId
                )
            }

        }
    }
}
