package com.example.android_advance.redux

import android.util.Log
import com.example.android_advance.model.request.userRequest
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto
import org.reduxkotlin.Reducer

data class AppState(val roomDto: roomDto, val userDto: UserDto, val userRegister: userRequest)

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

            is AddUserToListGroup -> {
                val updatedUserList = state.roomDto.user ?: ArrayList()
                val updatedUserListId = state.roomDto.listId ?: ArrayList()
                updatedUserListId.addAll(action.payload.map { it.id!! })
                updatedUserList.addAll(action.payload)
                state.copy(
                    roomDto = state.roomDto.copy(
                        user = updatedUserList,
                        listId = updatedUserListId
                    )
                )
            }

            is RemoveUserOutOfGroupAction -> {
                val updatedUserList = state.roomDto.user ?: ArrayList()
                updatedUserList.remove(action.payload)
                state.copy(roomDto = state.roomDto.copy(user = updatedUserList))
            }

            is AddUserRegister -> {
                state.copy(userRegister = action.payload)
            }


            else -> {
                state
            }
        }
    }
}