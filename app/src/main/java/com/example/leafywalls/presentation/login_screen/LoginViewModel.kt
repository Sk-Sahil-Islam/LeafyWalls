package com.example.leafywalls.presentation.login_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.leafywalls.common.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    var signInUiState = mutableStateOf(SignUpUiState())

    fun onEvent(event: SignUpUiEvent){
        when(event) {
            is SignUpUiEvent.EmailChange -> {
                signInUiState.value = signInUiState.value.copy(
                    email = event.email
                )
                validateEmail()
            }

            is SignUpUiEvent.PasswordChange -> {
                signInUiState.value = signInUiState.value.copy(
                    password = event.password
                )
            }

            is SignUpUiEvent.PasswordConfirmChange -> {}
        }
    }

    private fun validateEmail() {
        val emailResult = Validator.validateEmail(
            email = signInUiState.value.email
        )
        signInUiState.value = signInUiState.value.copy(
            emailError = emailResult.status
        )
    }


    fun validateAll(): Boolean {
        signInUiState.value.apply {
            return emailError
        }
    }
}