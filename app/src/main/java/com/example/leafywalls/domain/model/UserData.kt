package com.example.leafywalls.domain.model

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUrl: String? = null
)