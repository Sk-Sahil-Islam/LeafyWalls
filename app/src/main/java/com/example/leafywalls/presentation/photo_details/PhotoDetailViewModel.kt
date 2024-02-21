package com.example.leafywalls.presentation.photo_details

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafywalls.common.Constants
import com.example.leafywalls.common.Resource
import com.example.leafywalls.domain.repository.PhotoRepository
import com.example.leafywalls.domain.use_case.DownloadWallpaperUseCase
import com.example.leafywalls.domain.use_case.GetPhotoUseCase
import com.example.leafywalls.domain.use_case.SetWallpaperUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val repository: PhotoRepository,
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
        GetPhotoUseCase(repository).invoke(photoId = photoId).onEach { result->
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

    fun downloadWallpaper(
        url: String,
        downloadUrl: String,
        context: Context,
    ) {
        DownloadWallpaperUseCase().invoke(url = url, context, downloadUrl = downloadUrl).onEach { result ->

            when(result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        settingWallpaper = false
                    )
                    Toast.makeText(context, result.data, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        settingWallpaper = true
                    )
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

    fun setWallpaper(
        url: String,
        downloadUrl: String,
        fileName: String,
        context: Context,
        which: Int
    ) {
        SetWallpaperUseCase().invoke(url = url, context, fileName, which, downloadUrl = downloadUrl).onEach { result ->

            when(result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        settingWallpaper = false
                    )
                    Toast.makeText(context, result.data, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        settingWallpaper = true
                    )
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

