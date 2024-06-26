package com.example.android_advance.redux

import com.example.android_advance.model.request.userRequest
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto
import org.reduxkotlin.Store
import org.reduxkotlin.createStore
import org.reduxkotlin.createThreadSafeStore

object Store {
    var storeInstance: Store<AppState>? = null
    fun getStore(): Store<AppState>? {
        if (storeInstance == null) {
            storeInstance = createStore(
                ReducerSingleton.reducer,
                AppState(roomDto = roomDto(), userDto = UserDto(), userRegister = userRequest())
            )
        }
        return storeInstance
    }
}