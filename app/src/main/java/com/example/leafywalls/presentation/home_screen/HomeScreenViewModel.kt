package com.example.leafywalls.presentation.home_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(): ViewModel() {

    private val _selectedIndex = mutableStateOf(0)
    val selectedIndex = _selectedIndex

    fun updateIndex(index: Int) {
        _selectedIndex.value = index
    }
}