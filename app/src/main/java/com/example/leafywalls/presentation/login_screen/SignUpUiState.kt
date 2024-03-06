package com.example.leafywalls.presentation.login_screen

data class SignUpUiState (
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",

    var emailError: Boolean = true,
    var passwordError: Boolean = true
)