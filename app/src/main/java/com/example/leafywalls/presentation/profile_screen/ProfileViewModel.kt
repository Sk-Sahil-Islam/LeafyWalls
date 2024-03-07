package com.example.leafywalls.presentation.profile_screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafywalls.common.Constants
import com.example.leafywalls.common.Resource
import com.example.leafywalls.domain.model.UserData
import com.example.leafywalls.domain.repository.AuthRepository
import com.example.leafywalls.domain.use_case.GetPhotoUseCase
import com.example.leafywalls.presentation.photo_details.PhotoDetailState
import com.example.leafywalls.presentation.search_screen.SearchState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _currentState = MutableStateFlow(ProfileState())
    val currentState: StateFlow<ProfileState> get() = _currentState

    private val _prevState = MutableStateFlow(ProfileState())
    val prevState: StateFlow<ProfileState> get() = _prevState

    init {
        viewModelScope.launch {
            delay(100)
            updatePreviousState()
        }
    }
    fun updateProfile(context: Context, name: String) {
        viewModelScope.launch {
            when(val result = authRepository.updateProfile(name)) {
                is Resource.Error -> {
                    _currentState.value = _currentState.value.copy(
                        error = result.message ?: "Profile update unsuccessful",
                        isLoading = false
                    )

                }
                is Resource.Loading -> {
                    _currentState.value = _currentState.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _currentState.value = _currentState.value.copy(
                        error = "",
                        isLoading = false
                    )
                    Toast.makeText(context, "Profile update successful", Toast.LENGTH_SHORT).show()
                    updatePreviousState()
                }
            }
        }
    }

    fun updateCurrentState(userData: UserData) {
        _currentState.value.userData = userData
        Log.e("savedStateHandle", _prevState.value.userData.userName ?: "no value")
    }

    private fun updatePreviousState() {
        _prevState.value = _currentState.value.copy()
    }
}