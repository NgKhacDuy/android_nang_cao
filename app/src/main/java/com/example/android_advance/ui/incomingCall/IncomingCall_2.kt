package com.example.android_advance.ui.incomingCall

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.concurrent.TimeUnit

/*
    Dành cho người nhận khi nhận được 1 cuộc gọi.Có rung.Bấm accept để tắt rung.
 */
@Composable
fun IncomingCallReceiver_2(name: String,avtId:Int, modifier: Modifier = Modifier) {
    val avtUser = painterResource(id = avtId)
    Box(modifier= modifier
        .background(
            Color(
                69, 188, 204
            )
        )
        .fillMaxHeight()) {

        Text(
            text ="Incoming Call",
            modifier=modifier.padding(80.dp,100.dp,0.dp,0.dp),
            fontSize = 40.sp,
        )
        Image(
            painter = avtUser,
            contentDescription = null,
            modifier= modifier
                .padding(110.dp, 200.dp, 100.dp, 100.dp)
                .width(200.dp)
                .height(200.dp),

            )
        IncomingCallReceiver(name =name,
            modifier = Modifier
                .padding(0.dp, 100.dp, 0.dp, 0.dp)
        )
        //TimerScreen(
        //modifier = Modifier
        //  .padding(0.dp, 100.dp, 0.dp, 0.dp)
        //)


    }
}
/*class playAudio{
    var mediaPlayer: MediaPlayer? = null
    var context: Context? = null

    fun Audio(ct: Context?) {
        context = ct
    }
    fun playSound() {
        mediaPlayer = MediaPlayer.create(context,R.raw.rington)
        mediaPlayer?.start()
    }

    fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}*/
fun vibratePhone(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
    if (vibrator != null && vibrator.hasVibrator()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Vibrate with pattern on devices with Android Oreo (API 26) and above
            val vibrationEffect = VibrationEffect.createOneShot(20000, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            // Vibrate with pattern on devices with Android Nougat (API 25) and below
            vibrator.vibrate(500)
        }
    }


}
fun stopVibration(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
    vibrator?.cancel()
}
@Composable
fun IncomingCallReceiver(name: String, modifier: Modifier = Modifier) {
    //var playRington:playAudio?by remember { mutableStateOf(null) }
    var mediaPlayer: MediaPlayer? = null
    var context= LocalContext.current
    var showAcceptButton by remember { mutableStateOf(true) }
    var showTimer by remember { mutableStateOf(false) }
    var timerValue by remember { mutableStateOf(0L) }
    var countDownTimer: CountDownTimer? by remember { mutableStateOf(null) }



    // Function to start the timer
    fun startTimer() {
        countDownTimer?.cancel() // Cancel previous timer if any

        // Set the duration of the timer (in milliseconds)
        val durationMillis = TimeUnit.MINUTES.toMillis(5)

        // Create and start the CountDownTimer
        countDownTimer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update timer value
                timerValue = (durationMillis - millisUntilFinished) / 1000
            }

            override fun onFinish() {
                // Reset the timer value when it finishes
                timerValue = 0
                // Hide the timer when it finishes
                showTimer = false
            }
        }.start()

        // Show the timer
        showTimer = true
        showAcceptButton=false
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(100.dp,300.dp,0.dp,0.dp)
    ) {
        Text(
            text = name,
            fontSize = 40.sp,
            lineHeight = 50.sp,
            textAlign = TextAlign.Center,
            color= Color.Black
        )
    }
    // Timer display
    if (showTimer) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${timerValue / 60}:${timerValue % 60}",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(170.dp, 450.dp, 0.dp, 0.dp)
        )
    }

    Row(
        modifier = modifier.padding(50.dp, 300.dp, 0.dp, 0.dp)

    ) {
        if(showAcceptButton) {
            vibratePhone(context)

            Button(
                modifier = modifier
                    .clip(CircleShape)
                    .width(100.dp)
                    .height(100.dp),
                onClick = {// Hide the button and start the timer
                    showTimer = true
                    startTimer()
                    stopVibration(context)
                }, colors = ButtonDefaults.buttonColors(containerColor = Color(97, 237, 128))

            ) {
                Icon(
                    painter = painterResource(id = com.example.android_advance.R.drawable.icons8_call_96),
                    contentDescription = "null"
                )
            }
            Spacer(modifier = Modifier.width(90.dp))
            Button(
                modifier = modifier
                    .clip(CircleShape)
                    .width(100.dp)
                    .height(100.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(242, 116, 116))

            ) {
                Icon(painter = painterResource(id = com.example.android_advance.R.drawable.icons8_hang_up_96), contentDescription = "null")
            }

        }
        if (!showAcceptButton){
            Spacer(modifier = Modifier.width(90.dp))
            Button(
                modifier = modifier
                    .clip(CircleShape)
                    .width(100.dp)
                    .height(100.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(242, 116, 116))

            ) {
                Icon(painter = painterResource(id = com.example.android_advance.R.drawable.icons8_hang_up_96), contentDescription = "null")
            }
        }
    }

}
