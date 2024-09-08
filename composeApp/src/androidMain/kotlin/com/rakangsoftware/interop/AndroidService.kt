package com.rakangsoftware.interop

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AndroidService : Service {

    private val serviceScope = CoroutineScope(Dispatchers.Default)

    override fun onDone(callback:(String) -> Unit) {
        serviceScope.launch {
            callback("Waiting for Android!")
            delay(3000)
            callback("Android Done!")
        }
    }
}
