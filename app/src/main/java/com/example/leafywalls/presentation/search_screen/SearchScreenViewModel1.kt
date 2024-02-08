package com.example.leafywalls.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.domain.repository.PhotoRepository
import com.example.leafywalls.domain.use_case.GetSearchedPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel1 @Inject constructor(
    private val repository: PhotoRepository,
) : ViewModel() {

    private val _photos = MutableStateFlow<Flow<PagingData<PhotoDto>>>(emptyFlow())
    val photos: StateFlow<Flow<PagingData<PhotoDto>>> = _photos.asStateFlow()

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> get() = _searchState

    private val _prevState = MutableStateFlow(SearchState())
    val prevState: StateFlow<SearchState> get() = _prevState

    init {
        updatePrevState()
    }

    fun onEvent(searchEvent: SearchEvent1) {
        when (searchEvent) {

            is SearchEvent1.OnSearch -> {

                _searchState.value.apply {

                    _photos.value = GetSearchedPhotosUseCase(
                        repository = repository
                    ).invoke(
                        query = query,
                        orderBy = sortOption.ifEmpty { null },
                        safeSearch = safeSearch.ifEmpty { null },
                        color = color.ifEmpty { null },
                        orientation = orientation.ifEmpty { null }
                    )
                }
                updatePrevState()
            }

            is SearchEvent1.UpdateQuery -> {
                _searchState.value = _searchState.value.copy(
                    query = searchEvent.query
                )
            }

            is SearchEvent1.UpdateColor -> {
                val newColorOption = if (_searchState.value.color == searchEvent.color) "" else searchEvent.color
                _searchState.value = _searchState.value.copy(
                    color = newColorOption
                )
            }

            is SearchEvent1.UpdateOrientation -> {
                val newOrientationOption = if (_searchState.value.orientation == searchEvent.orientation) "" else searchEvent.orientation
                _searchState.value = _searchState.value.copy (
                    orientation = newOrientationOption
                )
            }

            is SearchEvent1.UpdateSort -> {
                val newSortOption = if (_searchState.value.sortOption == searchEvent.sort) "" else searchEvent.sort
                _searchState.value = _searchState.value.copy (
                    sortOption = newSortOption
                )
            }

            is SearchEvent1.UpdateSafeSearch -> {
                val newSafeSearch = if (_searchState.value.safeSearch == searchEvent.safeSearch) "" else searchEvent.safeSearch
                _searchState.value = _searchState.value.copy (

                    safeSearch = newSafeSearch
                )
            }

            is SearchEvent1.ResetToPreviousState -> {
                _searchState.value = _prevState.value
            }
        }
    }

    private fun updatePrevState() {
        _prevState.value = _searchState.value
    }
}
