package com.example.android_advance.ui.Group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.model.response.FriendList
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.ui.Message.MessageViewModel
import com.example.android_advance.ui.call_history.SearchCard
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(navController: NavController) {
    val viewModel = hiltViewModel<GroupScreenModel>()
    val userLiveData = viewModel.getUserInfo()
    val friendLiveData = viewModel.getFriendInfo()
    val userState: State<UserDto?> = userLiveData.observeAsState(initial = null)
    val friendState: State<List<FriendList?>?> = friendLiveData.observeAsState(initial = null)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        //Back button
        item {
            androidx.compose.material.IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = null, modifier = Modifier.size(26.dp))
            }
        }

        // Title
        item {
            Text(
                text = "Create Group",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center, // Căn giữa văn bản
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        // Group Description
        item {
            Text(
                text = "Group name",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            var inputValue by remember { mutableStateOf("") }

            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it},
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue, // Adjust as needed
                    unfocusedBorderColor = Color.Gray // Adjust as needed
                )
            )
        }

        // Group Admin
        item {
            Text(
                text = "Group Admin",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Admin Image
                Image(
                    painter = painterResource(id = R.drawable.user), // Replace with actual image
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                when (val user = userState.value) {
                    null -> {
                        // Show loading indicator or handle error
                    }
                    else -> {
                        user.name?.let {
                            Text(
                                text = it,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }

        // Invited Members
        item {
            Text(
                text = "Invited Members",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )

            friendState.value?.forEach { result ->
                if(result?.status != "Waiting for Accept"){
                    result?.user?.let { user ->
                        user.name?.let { userName ->
                            user.id?.let { userId ->
                                UserListItem(userName, userId, viewModel)
                            }
                        }
                    }
                }
            }
        }

        // Create Group Button
        item {
            Button(
                onClick = { viewModel.createRoom() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Create Group", color = Color.White)
            }
        }
    }
}

@Composable
fun UserListItem(text: String, userId: String, viewModel: GroupScreenModel) {
    val isAdded = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Invited Members List
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User Image
            Image(
                painter = painterResource(id = R.drawable.user), // Replace with actual image
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(8.dp)
            )

            // User Name
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            if(isAdded.value){
                IconButton(
                    onClick = {
                        viewModel.removeFriendId(userId)
                        isAdded.value = false
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Added")
                }
            } else {
                IconButton(
                    onClick = {
                        viewModel.addFriendId(userId)
                        isAdded.value = true
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    }
}

//@Composable
//fun GroupTypeButton(icon: ImageVector, text: String, selected: Boolean) {
//    Box(
//        modifier = Modifier
//            .clip(CircleShape)
//            .background(if (selected) Color.Blue else Color.Gray)
//            .clickable { /* Handle button click */ }
//            .padding(8.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(8.dp)
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Icon(imageVector = icon, contentDescription = null, tint = Color.White)
//            Text(text = text, color = Color.White)
//        }
//    }
//}
