package com.example.leafywalls.presentation.favorites_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafywalls.data.db.Favorite
import com.example.leafywalls.domain.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repository: PhotoRepository
): ViewModel() {

    init {
        loadState()
    }

    private val _state = MutableStateFlow<List<Favorite>>(emptyList())
    val state: StateFlow<List<Favorite>> get() = _state

    private fun loadState() {
        viewModelScope.launch {

            repository.getFavourite().collectLatest {
                _state.value = it
            }
        }
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.insertFavourite(favorite = favorite)
        }
    }
}