package com.example.leafywalls.presentation.favorites_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafywalls.data.db.Favorite
import com.example.leafywalls.domain.repository.PhotoRepository
import com.example.leafywalls.presentation.favorites_screen.componants.FavoriteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> get() = _state

    private fun loadState() {
        viewModelScope.launch {

            repository.getFavourite().collectLatest { favoriteList->
                _state.value.favorites = favoriteList.map { FavoriteItem(it) }
            }
        }
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.insertFavourite(favorite = favorite)
        }
    }

    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.deleteFavourites(favorite = favorite)
        }
    }

    fun updateSelected(index: Int) {
        _state.value.favorites[index].isSelected = !_state.value.favorites[index].isSelected
        _state.value = _state.value.copy(
            isAllSelect = isAllSelected()
        )
    }

    fun deselectAll() {
        _state.value.favorites.forEach {
            it.isSelected = false
        }
        _state.value = _state.value.copy(
            isAllSelect = false
        )
    }

    fun selectAll() {
        _state.value.favorites.forEach {
            it.isSelected = true
        }
        _state.value = _state.value.copy(
            isAllSelect = true
        )
    }

    fun deleteSelected() {
        val favorites1 = _state.value.favorites.filter {
            it.isSelected
        }.map {
            it.item
        }
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isDeleting = true
            )

            repository.deleteMultipleFavourite(favorites1)

            delay(1500)
            isMultiSelectChange()

            _state.value = _state.value.copy(
                isDeleting = false
            )
        }
    }

    fun isMultiSelectChange(boolean: Boolean? = null) {
        val isMultiSelect = _state.value.isMultiSelect
        if(boolean == null) {
            _state.value = _state.value.copy(
                isMultiSelect = !isMultiSelect
            )
        } else {
            _state.value = _state.value.copy(
                isMultiSelect = boolean
            )
        }

    }

    private fun isAllSelected(): Boolean {
        return _state.value.favorites.all { it.isSelected }
    }
}