package com.example.android_advance.ui.Home



import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.android_advance.R
import com.example.android_advance.navigation.Route
import com.google.firebase.annotations.concurrent.Background
import kotlin.math.round


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreenContent(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp) // Adjust the padding to control the height of the second box
        ) {
            Box(
                modifier = Modifier
                    .weight(1f),

            ) {
                // Content for the background image
                Image(
                    painter = painterResource(id = R.drawable.welcome),
                    contentDescription = null,
                    contentScale = ContentScale.Crop

                )

                Text(
                    text = "Your Text Here",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 40.dp)

                )
            }

            // Spacer with a specific height to create a separation between the first and second box
            Spacer(modifier = Modifier.height(50.dp))
        }

        // Second Box with a specific height
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f) // Adjust the height of the second box
                .align(Alignment.BottomStart)
                .background(Color.White)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))

        ) {
            // Content for the second Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Adjust padding as needed
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Three columns with Text elements
                Image(
                    painter = painterResource(id = R.drawable.welcome),
                    contentDescription = null,
                    modifier = Modifier

                        .size(50.dp)
                        .clip(CircleShape)
                        ,
                    contentScale = ContentScale.Crop
                )
                Column(

                ){
                        Text(text = "Column 2", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Column 3", fontSize = 16.sp, fontWeight = FontWeight.Normal)
                }
                Column(
                ){


                    Text(text = "Column 3", fontSize = 16.sp, fontWeight = FontWeight.Normal)
                    Image(painter = painterResource(
                        id = R.drawable.user),
                        contentDescription =null,
                        modifier = Modifier
                            .size(20.dp)
                            .offset(x=40.dp)
                        ,

                    )




                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f) // Adjust the height of the second box
                .align(Alignment.BottomStart)
                .background(Color.White)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.Black
                    )
                ),

        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(){
                    Image(painter = painterResource(
                        id = R.drawable.message),
                        contentDescription =null,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterHorizontally)
                        )
                    Text(text = "Message", fontSize = 16.sp, fontWeight = FontWeight.Normal)

                }
                Column(){
                    Image(painter = painterResource(
                        id = R.drawable.icons8_call_96),
                        contentDescription =null,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(text = "Call", fontSize = 16.sp, fontWeight = FontWeight.Normal)                }
                Column(){
                    Image(painter = painterResource(
                        id = R.drawable.user_nav),
                        contentDescription =null,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(text = "Contacts", fontSize = 16.sp, fontWeight = FontWeight.Normal)                }
                Column(){
                    Image(painter = painterResource(
                        id = R.drawable.setting),
                        contentDescription =null,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(text = "setting", fontSize = 16.sp, fontWeight = FontWeight.Normal)                }
            }


        }
    }
}

//@OptIn(ExperimentalComposeUiApi::class)

@Preview
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    HomeScreenContent(navController = navController)
}