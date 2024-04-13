package com.example.android_advance.ui.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android_advance.ui.search.SearchScreenModel

@Composable
fun ListRequest(viewModel: SearchScreenModel) {
    val requestState = viewModel.friendRequestResult.observeAsState()
    requestState.value?.forEach { result ->
        val user =
            if (result.idSender == result.user?.get(0)?.id) result.user?.get(0) else result.user?.get(
                1
            )
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = "https://mathiasfrohlich.gallerycdn.vsassets.io/extensions/mathiasfrohlich/kotlin/1.7.1/1581441165235/Microsoft.VisualStudio.Services.Icons.Default",
                    contentDescription = "kotlin",
                    modifier = Modifier
                        .height(60.dp)
                        .width(80.dp)
                )
                Column {
                    Text(text = user?.name!!)
                    Row {
                        Button(
                            onClick = {
                                viewModel.decideFriend(user.id!!, "Accept")
                            },
                            colors = ButtonDefaults.buttonColors().copy(containerColor = Color.Blue)
                        ) {
                            Text(text = "Accept")
                        }
                        Button(
                            onClick = {
                                viewModel.decideFriend(user.id!!, "Reject")
                            },
                            colors = ButtonDefaults.buttonColors()
                                .copy(containerColor = Color.White)
                        ) {
                            Text(text = "Reject", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}