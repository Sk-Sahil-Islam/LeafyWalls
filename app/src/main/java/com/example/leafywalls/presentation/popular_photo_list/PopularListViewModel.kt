package com.example.leafywalls.presentation.popular_photo_list

import androidx.lifecycle.ViewModel
import com.example.leafywalls.domain.use_case.GetPopularPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularListViewModel @Inject constructor(
    private val getPopularPhotosUseCase: GetPopularPhotosUseCase
): ViewModel() {
    val state = getPopularPhotosUseCase()
}