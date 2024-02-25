package com.example.android_advance.ui.Home



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.android_advance.R
import com.google.firebase.annotations.concurrent.Background

data class User(
    val avatar: Int, // Resource ID for the user's avatar
    val name: String,
    val lastMessage: String,
    val lastActive: String,
    val messageCount : Int
)
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun HomeScreen() {
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // First image
                    Image(
                        painter = painterResource(R.drawable.search),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White)
                        ,
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .padding(8.dp)
                        ,
                        contentScale = ContentScale.Crop
                    )

                    // Text overlay on top of the image
                    Text(
                        text = "Home",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .align(Alignment.TopCenter)
//                            .offset(y = 40.dp)
                    )

                    // Second image
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(Color.White) // Set the background color
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.person_avt),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(), // Make the image fill the Box
                            contentScale = ContentScale.Crop
                        )
                    }


                }





            }

            // Spacer with a specific height to create a separation between the first and second box
            Spacer(modifier = Modifier.height(50.dp))
        }

        // Second Box with a specific height
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f) // Adjust the height of the second box
                .align(Alignment.BottomStart)
                .background(Color.White)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val userList = listOf<User>(
                    User(R.drawable.person_avt, "Alex Linderson","How Are You ?","2 min",R.drawable.user),
                    User(R.drawable.person_avt, "Alex Linderson","How Are You ?","2 min",R.drawable.user),
                    User(R.drawable.person_avt, "Alex Linderson","How Are You ?","2 min",R.drawable.user),
                    User(R.drawable.person_avt, "Alex Linderson","How Are You ?","2 min",R.drawable.user) ,
                    User(R.drawable.person_avt, "Alex Linderson","How Are You ?","2 min",R.drawable.user),
                    User(R.drawable.person_avt, "Alex Linderson","How Are You ?","2 min",R.drawable.user),
                    User(R.drawable.person_avt, "Alex Linderson","How Are You ?","2 min",R.drawable.user),

                )
                for (user in userList) {
                    UserRow(user = user)
                }
            }

        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.15f) // Adjust the height of the second box
//                .align(Alignment.BottomStart)
//                .background(Color.White)
//                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
//                .border(
//                    border = BorderStroke(
//                        width = 1.dp,
//                        color = Color.Black
//                    )
//                ),
//
//        ){
////            Row(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .padding(16.dp),
////                horizontalArrangement = Arrangement.SpaceEvenly,
////                verticalAlignment = Alignment.CenterVertically
////            ){
////                Column(){
////                    Image(painter = painterResource(
////                        id = R.drawable.message),
////                        contentDescription =null,
////                        modifier = Modifier
////                            .size(20.dp)
////                            .align(Alignment.CenterHorizontally)
////                        )
////                    Text(text = "Message", fontSize = 16.sp, fontWeight = FontWeight.Normal)
////
////                }
////                Column(){
////                    Image(painter = painterResource(
////                        id = R.drawable.icons8_call_96),
////                        contentDescription =null,
////                        modifier = Modifier
////                            .size(20.dp)
////                            .align(Alignment.CenterHorizontally)
////                    )
////                    Text(text = "Call", fontSize = 16.sp, fontWeight = FontWeight.Normal)                }
////                Column(){
////                    Image(painter = painterResource(
////                        id = R.drawable.user_nav),
////                        contentDescription =null,
////                        modifier = Modifier
////                            .size(20.dp)
////                            .align(Alignment.CenterHorizontally)
////                    )
////                    Text(text = "Contacts", fontSize = 16.sp, fontWeight = FontWeight.Normal)                }
////                Column(){
////                    Image(painter = painterResource(
////                        id = R.drawable.setting),
////                        contentDescription =null,
////                        modifier = Modifier
////                            .size(20.dp)
////                            .align(Alignment.CenterHorizontally)
////                    )
////                    Text(text = "setting", fontSize = 16.sp, fontWeight = FontWeight.Normal)                }
////            }
//
//
//        }
    }
}

@Composable
fun UserRow(user: User) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Black,
                ),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)

            ), // Adjust padding as needed
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (
            modifier = Modifier.offset(x=(-20).dp).padding(vertical = 16.dp)
        ){
            Image(
                painter = painterResource(user.avatar),
                contentDescription = null,
                modifier = Modifier

                    .size(35.dp)
                    .clip(CircleShape)

                ,
                contentScale = ContentScale.Crop
            )
        }
        // Three columns with Text elements

        Column(
            modifier = Modifier.offset(x=(-30).dp)
        ){
                Text(text = user.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = user.lastMessage, fontSize = 16.sp, fontWeight = FontWeight.Normal)
        }
        Column(
//            modifier = Modifier.offset(x=(-20).dp)
        ){


            Text(text = user.lastActive, fontSize = 16.sp, fontWeight = FontWeight.Normal)
            Image(painter = painterResource(
                id = user.messageCount),
                contentDescription =null,
                modifier = Modifier
                    .size(20.dp)
                    .offset(x = 40.dp)
                ,

            )

        }
    }
}

//@OptIn(ExperimentalComposeUiApi::class)
//@Preview
//@Composable
//fun HomeScreen(navController: NavHostController) {
//    val navController = rememberNavController()
//    HomeScreenContent()
//}
//