package com.example.android_advance.ui.splash_screen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.example.android_advance.R
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.android_advance.navigation.Route
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val alpha = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        alpha.animateTo(1f, animationSpec = tween(500))
        delay(1500)
        navController.popBackStack()
        navController.navigate(Route.WelcomeScreen.route)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.White
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoaderAnimation(
            modifier = Modifier
                .height(screenHeight * 0.6f)
                .width(screenWidth * 0.6f),
            anim = R.raw.splash
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Zola", fontSize = 32.sp, modifier = Modifier.alpha(alpha.value))
    }
}

@Composable
fun LoaderAnimation(modifier: Modifier, anim: Int) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(anim))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}