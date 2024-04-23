package com.example.android_advance.ui.ListUser

import androidx.lifecycle.ViewModel
import com.example.android_advance.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListUserViewModel @Inject constructor() : ViewModel() {
    val store = Store.getStore()
    val roomDto = store?.state?.roomDto
    val userDto = store?.state?.userDto!!
}