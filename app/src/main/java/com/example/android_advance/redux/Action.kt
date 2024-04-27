package com.example.android_advance.redux

import com.example.android_advance.model.request.userRequest
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto

class AddRoomDto(val payload: roomDto)
class RemoveRoomDto
class AddUser(val payload: UserDto)

class AddUserToListGroup(val payload: ArrayList<UserDto>)
class RemoveUserOutOfGroupAction(val payload: UserDto)
class AddUserRegister(val payload: userRequest)