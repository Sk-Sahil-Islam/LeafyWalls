package com.example.leafywalls.presentation.search_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import com.example.leafywalls.data.db.History
import com.example.leafywalls.data.remote.dto.PhotoDto

data class SearchState(
    val histories: List<History> = emptyList(),
    val photos: PagingData<PhotoDto> = PagingData.empty(),
    //val isFocused: Boolean = false,
    val query: MutableState<String> = mutableStateOf("")
)