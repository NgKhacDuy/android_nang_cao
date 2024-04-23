package com.example.android_advance.redux

import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto
import org.reduxkotlin.Store
import org.reduxkotlin.createStore
import org.reduxkotlin.createThreadSafeStore

object Store {
    var storeInstance: Store<AppState>? = null
    fun getStore(): Store<AppState>? {
        if (storeInstance == null) {
            storeInstance = createThreadSafeStore(
                ReducerSingleton.reducer,
                AppState(roomDto = roomDto(), userDto = UserDto())
            )
        }
        return storeInstance
    }
}