package com.example.leafywalls.presentation.home_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.leafywalls.domain.repository.PhotoRepository
import com.example.leafywalls.domain.use_case.GetRandomPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: PhotoRepository
): ViewModel() {

    private val _selectedIndex = mutableStateOf(0)
    val selectedIndex = _selectedIndex


    fun updateIndex(index: Int) {
        _selectedIndex.value = index
    }

}