
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.model.content.CircleShape
import com.example.android_advance.R
import com.example.android_advance.components.ViewImg.ExpandedImage
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.Message.MessageViewModel
import com.example.android_advance.ui.Message.components.ChatItem
import com.example.android_advance.ui.Screen.SettingItem


//@Preview
@Composable
//fun OptionsMenu() {
fun OptionsMenu(navController: NavController, partnerName: String, idRoom:String) {
//    var partnerName by remember { mutableStateOf("duyhuynhlam") }
    var searchDialogShown by remember { mutableStateOf(false) }
    var returnKeyword by remember { mutableStateOf("") }
    var request_key by remember { mutableStateOf("") }

    val viewModel = hiltViewModel<MessageViewModel>()
    viewModel.savedStateHandle.set("roomId", idRoom)
    val messageState = viewModel.onNewMessage.observeAsState()

    messageState.value?.let {messages ->

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(messages.size) { item ->
            ChatItem(messages[item], true)
        }
    }



}


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menu Options") },
                elevation = 4.dp,
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            )
        },
        content = { content ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState()),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content.let {

                    SettingItem(
                        icon = Icons.Default.Person,
                        title = "Partner name: $partnerName${if (returnKeyword.isNotEmpty()) " ($returnKeyword)" else ""}",
                        onClick = {}
                    )
                    SettingItem(
                        icon = Icons.Default.PersonAdd,
                        title = "NickName:"  ,
                        onClick = {
                            searchDialogShown = true
                            request_key = "nickname"
                        }
                    )

                    SettingItem(
                        icon = Icons.Default.Search,
                        title = "Search keywords",
                        onClick = {
                            searchDialogShown = true
                            request_key = "search_key"
                        }
                    )
                    SettingItem(
                        icon = Icons.Default.Group,
                        title = "Create Group with $partnerName ${if (returnKeyword.isNotEmpty()) " ($returnKeyword)" else ""}",
                        onClick = {
                            navController.navigate(Route.CreateGroupScreen.route)
                        }
                    )
                    SettingItem(
                        icon = Icons.Default.Block,
                        title = "Block $partnerName ${if (returnKeyword.isNotEmpty()) " ($returnKeyword)" else ""}",
                        onClick = {}
                    )
                    SettingItem(
                        icon = Icons.Default.IosShare,
                        title = "Share $partnerName ${if (returnKeyword.isNotEmpty()) " ($returnKeyword)" else ""}",
                        onClick = {}
                    )
//                    DisplayImages()
                    messageState.value?.let {messages ->
                        DisplayImages(messages)
                    }

                    
                }
            }
        }
    )

    if (searchDialogShown) {
        if (request_key == "nickname"){
            SearchDialog(
                onDismissRequest = {
                    // Handle dialog dismiss if needed
                    searchDialogShown = false
                },
                onSearch = { keyword ->
                    returnKeyword = keyword
//                val previousBackEntry = navController.previousBackStackEntry
//                previousBackEntry?.savedStateHandle?.set("key_words_return", keyword)
//                navController.popBackStack()
                    Log.e("nickname key", returnKeyword)
                    searchDialogShown = false
                },
                request = "Nickname"
            )
        }
        else if (request_key == "search_key"){
            SearchDialog(
                onDismissRequest = {
                    // Handle dialog dismiss if needed
                    searchDialogShown = false
                },
                onSearch = { keyword ->
                    returnKeyword = keyword
                    val previousBackEntry = navController.previousBackStackEntry
                    previousBackEntry?.savedStateHandle?.set("key_words_return", keyword)
                    navController.popBackStack()
//                    Log.e("search key", returnKeyword)
                    searchDialogShown = false
                },
                request = "Search keyword"
            )
        }

    }
}





@Composable
fun SearchDialog(
    onDismissRequest: () -> Unit,
    onSearch: (String) -> Unit,
    request:String
) {
    var returnKeyword by remember { mutableStateOf("") }
//    var requestTitle by remember{ mutableStateOf("") }
//    if (request == "search_key"){
//        requestTitle = "Enter Keyword"
//    }
//    else if (request == "nickname"){
//        requestTitle = "Enter a nickname"
//
//    }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = request, fontWeight = FontWeight.Bold)
        },
        text = {
            OutlinedTextField(
                value = returnKeyword,
                onValueChange = { returnKeyword = it },
                placeholder = { Text("Search...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onSearch(returnKeyword)
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("OK", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            ) {
                Text("Cancel", fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        properties = DialogProperties(dismissOnClickOutside = false)
    )
}
@Composable
fun DisplayImages(messages: List<messageDto>) {
    val imageMessages = messages.filter{it.image.isNotEmpty()}
    val imageUrls = imageMessages.flatMap {it.image.map { image -> image.url}}
    var isSelected = remember { mutableStateOf(false) }
    var indexSelected = remember { mutableIntStateOf(0) }

    LazyColumn {
        items(imageUrls.chunked(4)){rowImage ->
            Row (
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                for (imageUrl in rowImage){
                    imageUrl.let {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .border(2.dp, Color.Gray)
                                .weight(1f)
                                .clickable {
                                    isSelected.value = true
                                    indexSelected.value = imageUrls.indexOf(it)
                                }

                        )


                    }
                }
            }

        }
    }
    if (isSelected.value) {
        imageUrls[indexSelected.intValue]?.let {
            ExpandedImage(it) {
                isSelected.value = false
            }
        }
    }


}
