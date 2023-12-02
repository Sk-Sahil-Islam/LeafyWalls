package com.example.leafywalls.presentation.photo_list

import androidx.paging.PagingData
import com.example.leafywalls.data.remote.dto.PhotoDto
import kotlinx.coroutines.flow.Flow

data class PhotoListState(
    val isLoading: Boolean = false,
    val photos: Flow<PagingData<PhotoDto>>? = null,
    val error: String = ""

)
