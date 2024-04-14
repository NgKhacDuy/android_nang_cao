import android.os.Message
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.android_advance.R
import com.example.android_advance.components.ImagePicker.ImagePicker
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.BottomNavigation.ChildRoute
import com.example.android_advance.ui.Home.HomeScreenViewModel
import com.example.android_advance.ui.Message.MessageViewModel
import com.example.android_advance.ui.Message.components.ChatBox
import com.example.android_advance.ui.Message.components.ChatItem
import com.example.android_advance.ui.Message.components.ListImage
import com.example.android_advance.utils.common.ConvertDateTime
import com.example.android_advance.utils.common.MessageEnum
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun ChatScreen(
    model: List<messageDto>,
    onSendChatClickListener: (String, String, List<String>) -> Unit,
    modifier: Modifier,
    onClickBack: () -> Unit,
    db: DatabaseHelper,
    partnerName: String,
    navController: NavController,
    idRoom: String
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val viewModel = hiltViewModel<HomeScreenViewModel>()
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

                IconButton(onClick = {
                    navController.navigate(Route.listUserGroupScreen.withArgs(idRoom))
                }) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
        Column {
            if (!model.isNullOrEmpty()) {
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
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Occupies all remaining space after ChatBox
                        .padding(top = (screenHeight * 0.1f).dp),
                ) {

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
