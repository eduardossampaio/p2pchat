package com.apps.esampaio.p2pchat.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userName: String,
    val imageUrl: String?,
)
