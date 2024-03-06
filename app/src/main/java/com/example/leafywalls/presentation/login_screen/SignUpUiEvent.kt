package com.example.leafywalls.presentation.login_screen

sealed class SignUpUiEvent {
    data class EmailChange(val email: String): SignUpUiEvent()
    data class PasswordChange(val password: String): SignUpUiEvent()
    data class PasswordConfirmChange(val confirmPassword: String): SignUpUiEvent()

}