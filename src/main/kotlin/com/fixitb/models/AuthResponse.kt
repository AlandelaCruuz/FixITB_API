package com.fixitb.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
)