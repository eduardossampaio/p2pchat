package com.apps.esampaio.p2pchat.core.model

data class Chat(
    val id: Int,
    val userName: String,
    val lastMessage: String,
    val avatarUrl: String
)