package com.example.android_advance.ui.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_advance.R
import com.example.android_advance.ui.search.SearchScreenModel

@Composable
fun ListSearch(viewModel: SearchScreenModel) {
    val poppinsFamily = FontFamily(Font(R.font.poppins_medium))
    val searchState = viewModel.searchResults.observeAsState()
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        searchState.value?.forEach { result ->
            result.name?.let {
                SearchCard(
                    R.drawable.user_solid,
                    it,
                    "User",
                    result.friends,
                    result.id!!,
                    viewModel,
                    isGroupSearch = false
                )
            }
            TabRowDefaults.Divider(
                color = Color.LightGray, thickness = 0.7.dp, modifier = androidx.compose.ui.Modifier
                    .width((screenWidth / 4).dp)
                    .align(Alignment.End)
            )
        }
    }
}