package com.example.leafywalls.presentation.profile_screen

import com.example.leafywalls.domain.model.UserData


data class ProfileState(
    var userData: UserData = UserData(),
    val error: String = "",
    val isLoading: Boolean = false
)