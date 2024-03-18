package com.example.android_advance.utils.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debounce {
    fun CoroutineScope.debounce(
        waitMs: Long = 300L,
        action: () -> Unit
    ): Job {
        return launch {
            delay(waitMs)
            action()
        }
    }
}