import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.ui.ListUser.ListUserViewModel
import com.example.android_advance.ui.ListUser.component.SearchItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteMember(navController: NavController) {
    val viewModel = hiltViewModel<ListUserViewModel>()
    val SearchState = viewModel.searchResults.observeAsState()
    val debounceJob = remember { mutableStateOf<Job?>(null) }
    var searchValue by remember { mutableStateOf("") }
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
//    val users = listOf("John Doe", "Jane Smith", "Michael Johnson")

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            verticalAlignment = Alignment.CenterVertically
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
                " Invite Members",
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .weight(5f)
                    .padding(start = 3.dp)
            )
            Spacer(Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material3.OutlinedTextField(
            value = searchValue,
            onValueChange = { newValue ->
                searchValue = newValue
                if (newValue.isNotEmpty() && newValue.isNotBlank()) {
                    debounceJob.value?.cancel()
                    debounceJob.value = CoroutineScope(Dispatchers.Main).launch {
                        delay(1000L)
                        viewModel.performSearch(newValue)
                    }
                }
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
        Row() {
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier
//                .width((screenWidth * 0.2f).dp)
                    .padding(8.dp)
                    .clip(
                        RoundedCornerShape(corner = CornerSize(16.dp))
                    )
                    .background(Color(0xFF62bd5f))
                    .padding(2.dp)

            ) {
                IconButton(
                    onClick = { viewModel.addUserToGroup() },

                    ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Add all user to group",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.Default.GroupAdd,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                }
            }
        }



        LazyColumn() {
            SearchState.value.let {
                items(it?.size ?: 0) { index ->
                    val user = it?.get(index)
                    if (user != null) {
                        SearchItem(user = user, viewModel = viewModel)
                    }
                }
            }

        }
    }
}

//@Composable
//fun UsersItem(name: String, userId: String, viewModel: ListUserViewModel) {
//    val isAdded = remember { mutableStateOf(false) }
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
//        if (isAdded.value){
//            IconButton(onClick = {
//                viewModel.removeFriendId(userId)
//                isAdded.value = false
//            }) {
//                Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = "Add User"
//                )
//            }
//        } else {
//            IconButton(onClick = {
//                viewModel.removeFriendId(userId)
//                isAdded.value = true
//            }) {
//                Icon(
//                    imageVector = Icons.Default.PersonAdd,
//                    contentDescription = "Remove User"
//                )
//            }
//        }
//    }
//}