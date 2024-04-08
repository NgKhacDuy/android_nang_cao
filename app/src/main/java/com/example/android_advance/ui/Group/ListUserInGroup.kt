package com.example.android_advance.ui.Group

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_advance.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Giả sử bạn có một data class đại diện cho thành viên trong nhóm
data class User(val name: String, val avatar: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUserInGroup() {
    // Dữ liệu mẫu bên trong hàm
    val users = listOf(
        User("Nguyễn Văn A", android.R.drawable.sym_def_app_icon),
        User("Trần Thị B", android.R.drawable.sym_def_app_icon)
    )

    var searchValue by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material.IconButton(onClick = {

            }) {
                androidx.compose.material3.Icon(
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
            Spacer(Modifier
                .weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* TODO: Handle add member */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text("Invite Members")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "List Members",
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.OutlinedTextField(
            value = searchValue,
            onValueChange = { newValue ->
                searchValue = newValue
            },
            label = { androidx.compose.material3.Text("Search") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray
            )

        )
        LazyColumn {
            items(users) { user ->
                UserItem(user)
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.user), contentDescription = "User Avatar", modifier = Modifier.size(40.dp).padding(end = 8.dp))
        Text(text = user.name)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListUserInGroup() {
    ListUserInGroup()
}
