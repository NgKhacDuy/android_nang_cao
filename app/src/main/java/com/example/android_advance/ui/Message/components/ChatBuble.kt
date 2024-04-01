import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.BottomNavigation.ChildRoute
import com.example.android_advance.utils.common.ConvertDateTime

@Composable
fun ChatScreen(
    model: List<messageDto>,
    onSendChatClickListener: (String) -> Unit,
    modifier: Modifier,
    onClickBack: () -> Unit,
    db: DatabaseHelper,
    partnerName: String,
    navController: NavController,
    idRoom: String
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (_, chatBox) = createRefs()
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp
        val listState = rememberLazyListState()
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(model.size) {
            listState.animateScrollToItem(model.lastIndex)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    onClickBack()
                }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = null, modifier = Modifier.size(26.dp))
                }
                Image(
                    painter = painterResource(
                        id = R.drawable.person_avt
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.offset(x = 25.dp)
                ) {
                    Text(
                        text = partnerName,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .offset(x = 0.dp)
                    .width(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navController.navigate(Route.VideoScreen.withArgs(idRoom))
                }) {
                    Icon(
                        Icons.Rounded.Call,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
//                IconButton(onClick = {
////                navController.popBackStack()
//                }) {
//                    Icon(
//                        Icons.Rounded.Videocam,
//                        contentDescription = null,
//                        modifier = Modifier.size(28.dp)
//                    )
//                }
            }
        }
        Column {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Occupies all remaining space after ChatBox
                    .padding(top = (screenHeight * 0.1f).dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(model.size) { item ->
                    ChatItem(model[item], db.getUser().first().id == model[item].senderId)
                }
            }
            // Use Spacer to push ChatBox to the bottom
//            Spacer(modifier = Modifier.weight(1f)) // This Spacer will take up all available space, pushing the ChatBox to the bottom
            ChatBox(
                onSendChatClickListener,
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(bottom = 16.dp) // Add padding to ensure it's not touching the bottom edge
            )
        }

    }
}

@Composable
fun ChatItem(message: messageDto, isSender: Boolean) {
    val convertDateTime: ConvertDateTime = ConvertDateTime()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .align(if (isSender) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (isSender) 48f else 0f,
                        bottomEnd = if (isSender) 0f else 48f
                    )
                )
                .background(Color(0xFFD0BCFF))
                .padding(16.dp)
        ) {
            message.content?.let { Text(text = it) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBox(
    onSendChatClickListener: (String) -> Unit,
    modifier: Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = "Type something")
            }
        )
        IconButton(
            onClick = {
                val msg = chatBoxValue.text
                if (msg.isBlank()) return@IconButton
                onSendChatClickListener(chatBoxValue.text)
                chatBoxValue = TextFieldValue("")
            },
            modifier = Modifier
                .clip(CircleShape)
                .background(color = Color(0xFFD0BCFF))
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Send",
                modifier = Modifier.fillMaxSize().padding(8.dp)
            )
        }
    }
}