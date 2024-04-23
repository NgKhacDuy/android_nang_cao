package com.example.android_advance.redux

import android.util.Log
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto
import org.reduxkotlin.Reducer

data class AppState(val roomDto: roomDto, val userDto: UserDto)

object ReducerSingleton {
    val reducer: Reducer<AppState> = { state, action ->
        when (action) {
            is AddRoomDto -> {
                state.copy(roomDto = action.payload)
            }

            is RemoveRoomDto -> {
                state.copy(roomDto = roomDto())
            }

            is AddUser -> {
                state.copy(userDto = action.payload)
            }

            else -> {
                state
            }
        }
    }
}