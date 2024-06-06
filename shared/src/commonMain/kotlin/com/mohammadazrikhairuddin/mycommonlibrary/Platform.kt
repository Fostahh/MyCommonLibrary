package com.mohammadazrikhairuddin.mycommonlibrary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform