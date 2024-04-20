package com.example.android_advance.redux

import android.util.Log
import com.example.android_advance.model.response.roomDto
import org.reduxkotlin.Reducer

data class AppState(val roomDto: roomDto)

object ReducerSingleton {
    val reducer: Reducer<AppState> = { state, action ->
        when (action) {
            is Increment -> {
                Log.d("Reducer", "${action.payload.id})")
                state.copy(roomDto = action.payload)
            }

            is Decrement -> {
                state.copy(roomDto = roomDto())
            }

            else -> {
                state
            }
        }
    }
}