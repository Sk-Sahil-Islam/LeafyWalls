package com.example.leafywalls.presentation.photo_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.leafywalls.common.Resource
import com.example.leafywalls.domain.use_case.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
): ViewModel() {

//    private val _state = mutableStateOf(PhotoListState())
//    val state: State<PhotoListState> = _state

    val state = getPhotosUseCase()

//    init {
//        getPhotos()
//    }
//
//    private fun getPhotos() {
//        getPhotosUseCase().onEach { result->
//            when(result) {
//                is Resource.Error -> {
//                    _state.value = PhotoListState(
//                        error = result.message ?: "An unexpected error occurred"
//                    )
//                }
//                is Resource.Loading -> {
//                    _state.value = PhotoListState(isLoading = true)
//                }
//                is Resource.Success -> {
//                    _state.value = PhotoListState(photos = result.data)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

}