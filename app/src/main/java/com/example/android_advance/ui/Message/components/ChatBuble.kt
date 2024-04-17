
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.ui.Message.components.ChatBox
import com.example.android_advance.ui.Message.components.ChatItem
import com.google.gson.Gson

@Composable
fun ChatScreen(
    model: List<messageDto>,
    onSendChatClickListener: (String, String, List<String>) -> Unit,
    modifier: Modifier,
    onClickBack: () -> Unit,
    db: DatabaseHelper,
    partnerName: String,
    navController: NavController,
    idRoom: String,
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (_, chatBox) = createRefs()
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp
        val listState = rememberLazyListState()
        val focusRequester = remember { FocusRequester() }

        val keyWordsReturn = navController.currentBackStackEntry?.savedStateHandle?.get<String>("key_words_return")
        LaunchedEffect(model.size) {
            listState.animateScrollToItem(model.lastIndex)
        }

        if (!keyWordsReturn.isNullOrBlank()){
            LaunchedEffect(model) {
                Log.e("key_words_return", "$keyWordsReturn")
                Log.e("model size", "${model.size}")
                keyWordsReturn.let {
                    val indexMsg = model.indexOfLast { it.content == keyWordsReturn }
                    if (indexMsg != -1) {
                        listState.animateScrollToItem(indexMsg)

                        Log.e("msg found ok", keyWordsReturn)

                    } else {
                        Log.e("error", "Message not found in model")
                    }
                }

            }
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

                    navController.navigate(Route.OptionsMenuChat.withArgs(partnerName, idRoom ))
                }) {
                    Icon(
                        Icons.Rounded.Menu,
                        contentDescription = null,
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
