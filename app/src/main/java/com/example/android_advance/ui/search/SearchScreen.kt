package com.example.android_advance.ui.call_history

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android_advance.R
import com.example.android_advance.model.response.FriendsDto
import com.example.android_advance.ui.components.AlertDialogComponent
import com.example.android_advance.ui.search.SearchScreenModel
import com.example.android_advance.ui.search.component.ListRequest
import com.example.android_advance.ui.search.component.ListRoom
import com.example.android_advance.ui.search.component.ListSearch
import com.example.android_advance.ui.search.component.SearchCard
import kotlinx.coroutines.*

@Composable
fun SearchScreenPP(navController: NavController) {
    val viewModel = hiltViewModel<SearchScreenModel>()
    val poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    var searchValue by remember { mutableStateOf("") }
    val debounceJob = remember { mutableStateOf<Job?>(null) }
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                searchValue = ""
            },
        ) {
            Icon(
                Icons.Default.Clear,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    viewModel.navigateBack(navController)
                }) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .width((screenWidth * 0.4f).dp)
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon_without_circle),
                            null
                        )
                    },
                    trailingIcon = if (searchValue.isNotBlank()) trailingIconView else null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF3F6F6),
                        unfocusedContainerColor = Color(0xFFF3F6F6),
                        disabledContainerColor = Color(0xFFF3F6F6),
                        focusedBorderColor = Color(0xFFF3F6F6),
                        unfocusedBorderColor = Color(0xFFF3F6F6),
                        focusedLabelColor = Color.Black
                    ),
                    value = searchValue,
                    onValueChange = { newValue ->
                        searchValue = newValue
                        if (newValue.isNotEmpty() && newValue.isNotBlank()) {
                            debounceJob.value?.cancel()
                            debounceJob.value = CoroutineScope(Dispatchers.Main).launch {
                                delay(500L)
                                viewModel.performSearch(newValue)
                            }
                        }
                    },
                    label = { Text("Search", fontFamily = poppinsFamily) },
                    singleLine = true
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                        .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 20.dp)
                        .background(color = Color.Transparent),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ListSearch(viewModel)
                    ListRoom(viewModel)
                    ListRequest(viewModel)
                }
            }
        }
    }
    if (viewModel.isShowDialog.value) {
        AlertDialogComponent(
            onDismissRequest = viewModel.infoDialog.value.onDismissRequest,
            onConfirmation = viewModel.infoDialog.value.onConfirmation,
            dialogTitle = viewModel.infoDialog.value.dialogTitle,
            dialogText = viewModel.infoDialog.value.dialogText,
            positiveText = viewModel.infoDialog.value.positiveText,
            negativeText = viewModel.infoDialog.value.negativeText
        )
    }
}
