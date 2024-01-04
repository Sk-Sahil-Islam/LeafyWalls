package com.example.leafywalls.presentation.photo_details

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LOG_TAG
import com.example.leafywalls.common.Constants
import com.example.leafywalls.common.Resource
import com.example.leafywalls.domain.use_case.GetPhotoUseCase
import com.example.leafywalls.domain.use_case.SetWallpaperUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.URL
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



    fun setWallpaper(
        url: String,
        context: Context,
        which: Int
    ) {
        SetWallpaperUseCase().invoke(url = url, context, which = which).onEach { result ->

            when(result) {
                is Resource.Error -> {
                    Toast.makeText(context, result.data, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        settingWallpaper = true
                    )
                    Log.e("loading for setting wallpaper", "Loading")
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        settingWallpaper = false
                    )
                    Toast.makeText(context, result.data, Toast.LENGTH_SHORT).show()
                }
            }
        }.launchIn(viewModelScope)
    }
}

