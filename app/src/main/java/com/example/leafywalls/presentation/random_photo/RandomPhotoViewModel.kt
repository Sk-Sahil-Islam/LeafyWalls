package com.example.leafywalls.presentation.random_photo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.leafywalls.domain.repository.PhotoRepository
import com.example.leafywalls.domain.use_case.GetRandomPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RandomPhotoViewModel @Inject constructor(
    private val getRandomPhotoUseCase: GetRandomPhotoUseCase
): ViewModel() {

    val state = getRandomPhotoUseCase()
}