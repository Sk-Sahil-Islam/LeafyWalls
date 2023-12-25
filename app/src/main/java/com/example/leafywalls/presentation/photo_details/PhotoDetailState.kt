package com.example.leafywalls.presentation.photo_details

import com.example.leafywalls.domain.model.PhotoDetail

data class PhotoDetailState(
    val isLoading: Boolean = false,
    val photo: PhotoDetail? = null,
    val error: String = "",
    val settingWallpaper: Boolean = false
)
