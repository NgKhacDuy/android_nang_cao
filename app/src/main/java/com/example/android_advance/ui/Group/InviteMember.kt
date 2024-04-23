//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.FloatingActionButton
//import androidx.compose.material.Icon
//import androidx.compose.material.IconButton
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.AddCircleOutline
//import androidx.compose.material.icons.rounded.ArrowBack
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.android_advance.R
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun InviteMember(navController: NavController) {
//    var searchValue by remember { mutableStateOf("") }
//    val users = listOf("John Doe", "Jane Smith", "Michael Johnson")
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 25.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = {
//                navController.popBackStack()
//            }) {
//                Icon(
//                    Icons.Rounded.ArrowBack,
//                    contentDescription = null,
//                    modifier = Modifier.size(26.dp)
//                )
//            }
//            Spacer(Modifier.weight(1f))
//            Text(
//                " Invite Members",
//                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
//                modifier = Modifier
//                    .weight(5f)
//                    .padding(start = 3.dp)
//            )
//            Spacer(Modifier.weight(1f))
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        androidx.compose.material3.OutlinedTextField(
//            value = searchValue,
//            onValueChange = { newValue ->
//                searchValue = newValue
//            },
//            label = { androidx.compose.material3.Text("Search") },
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            singleLine = true,
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = Color.Blue,
//                unfocusedBorderColor = Color.Gray
//            )
//        )
//
//        val scrollState = rememberScrollState()
//        Column(modifier = Modifier.verticalScroll(scrollState)) {
//            users.forEach { user ->
//                UsersItem(name = user)
//            }
//        }
//
//        FloatingActionButton(
//            onClick = { /* Handle add user action */ },
//            backgroundColor = Color.LightGray,
//            modifier = Modifier
//                .padding(16.dp)
//                .align(Alignment.End)
//        ) {
//            Icon(
//                Icons.Default.Add,
//                contentDescription = "Add User",
//                tint = Color.White
//            )
//        }
//    }
//}
//
//@Composable
//fun UsersItem(name: String) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.user),
//            contentDescription = "User Avatar",
//            modifier = Modifier
//                .size(40.dp)
//                .padding(end = 8.dp)
//        )
//        Text(text = name)
//        Spacer(Modifier.weight(1f))
//        IconButton(onClick = { /* Handle delete user action */ }) {
//            Icon(
//                imageVector = Icons.Default.AddCircleOutline,
//                contentDescription = "Add User",
//                tint = Color.Red
//            )
//        }
//    }
//}