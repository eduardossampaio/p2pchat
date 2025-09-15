package com.apps.esampaio.p2pchat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform