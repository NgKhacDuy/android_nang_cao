import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
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
import com.example.android_advance.ui.Screen.SettingItem



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OptionsMenu(navController: NavController, partnerName: String) {

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
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Partner name: $partnerName",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
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
                    onClick = {}
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

