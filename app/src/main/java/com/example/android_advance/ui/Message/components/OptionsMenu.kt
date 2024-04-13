
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.ui.BottomNavigation.ChildRoute
import com.example.android_advance.ui.Screen.SettingItem
import com.google.gson.Gson


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OptionsMenu(navController: NavController, partnerName: String, idRoom:String, model:List<messageDto> ? = null) {

    var showDialog by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current


    Scaffold(
        topBar = {
            Row (
                horizontalArrangement = Arrangement.SpaceAround
            ){

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
                                Icons.Rounded.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                )
            }


        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                ,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Partner name: $partnerName",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                model?.forEach{
                    message -> (
                            Text(text = "${message.content}")

                            )
                }
                SettingItem(
                    icon = Icons.Default.Person,
                    title = "Partner Name: $partnerName",
                    onClick = {}
                )


                // Settings List
                SettingItem(
                    icon = Icons.Default.Abc,
                    title = "Nicknames",
                    onClick = {showDialog = true}
                )
                SettingItem(
                    icon = Icons.Default.Group,
                    title = "Create Group",
                    onClick = {}
                )
                SettingItem(
                    icon = Icons.Default.Search,
                    title = "Search Conversation",
                    onClick = {
                        val gson = Gson()
                        val modelJson = gson.toJson(model)
                        navController.navigate(ChildRoute.MessageScreen.withArgs(idRoom,partnerName,"4"))
                    }
                )
                SettingItem(
                    icon = Icons.Default.Block,
                    title = "Block",
                    onClick = {}
                )
                SettingItem(
                    icon = Icons.Default.IosShare,
                    title = "Share Contact",
                    onClick = {}
                )


            }
            // Show dialog to change partner name
            if (showDialog) {
                ChangeNameDialog(
                    currentName = partnerName,
                    onNameChanged = {
//                        newName ->
//                        partnerName = newName
//                        showDialog = false
                        // You can perform any other actions here like updating the partner name in your app
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }

    )
}


@Composable
fun ChangeNameDialog(
    currentName: String,
    onNameChanged: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .width(300.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = 8.dp
        ) {
            var newName by remember { mutableStateOf(currentName) }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Change Partner Name",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("New Name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onNameChanged(newName)
                            onDismiss()
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { onDismiss() },
//                            modifier = Modifier.background(color = Color.Red)
                        ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

