package com.apps.esampaio.p2pchat.core.model

data class Message(
    val id: Int,
    val text: String,
    val author: Author,
    val timestamp: String
)