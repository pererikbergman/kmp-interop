package com.rakangsoftware.interop

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform