package com.example.leafywalls.presentation.photo_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafywalls.common.Constants
import com.example.leafywalls.common.Resource
import com.example.leafywalls.domain.use_case.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(PhotoDetailState())
    val state: State<PhotoDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_PHOTO_ID)?.let { photoId->
            getPhoto(photoId)
        }
    }

    private fun getPhoto(photoId: String) {
        getPhotoUseCase(photoId = photoId).onEach { result->
            when(result) {
                is Resource.Error -> {
                    _state.value = PhotoDetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = PhotoDetailState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = PhotoDetailState(photo = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}