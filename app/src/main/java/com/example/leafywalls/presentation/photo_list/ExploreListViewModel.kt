package com.example.leafywalls.presentation.photo_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.leafywalls.domain.use_case.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreListViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
): ViewModel() {

    val state = getPhotosUseCase()

}