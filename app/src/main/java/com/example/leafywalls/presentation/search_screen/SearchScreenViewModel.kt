package com.example.leafywalls.presentation.search_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.leafywalls.data.db.History
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.domain.repository.PhotoRepository
import com.example.leafywalls.domain.use_case.GetHistoryUseCase
import com.example.leafywalls.domain.use_case.GetSearchedPhotosUseCase
import com.example.leafywalls.domain.use_case.InsertHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val insertHistoryUseCase: InsertHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private var _orderBy = mutableStateOf("relevant")
    private var _safeSearch = mutableStateOf("low")
    private var _color = mutableStateOf("")
    private var _orientation = mutableStateOf("")



    private var histories = getHistoryUseCase()

    private val _photos = MutableStateFlow<Flow<PagingData<PhotoDto>>>(emptyFlow())
    val photos: StateFlow<Flow<PagingData<PhotoDto>>> = _photos.asStateFlow()

    private val _state = MutableStateFlow(SearchState())
    val state =
        combine(_state, histories) { historyState, histories ->
            historyState.copy(
                histories = histories
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SearchState())

    fun onEvent(searchEvent: SearchEvent) {
        when (searchEvent) {
            SearchEvent.ClearHistory -> {
                viewModelScope.launch {
                    repository.clearHistory()
                }
            }

            is SearchEvent.DeleteHistory -> {

                viewModelScope.launch {
                    repository.deleteHistory(searchEvent.history)
                }
            }

            is SearchEvent.OnSearch -> {
                val history = History(text = state.value.query.value)

                if (history.text.isNotBlank()) {
                    _photos.value = GetSearchedPhotosUseCase(
                        repository = repository
                    ).invoke(
                        history.text,
                        _orderBy.value,
                        _safeSearch.value,
                        color = _color.value.ifEmpty { null },
                        orientation = _orientation.value.ifEmpty { null }
                    )

                    insertHistoryUseCase(history)
                }
            }
        }
    }


    private val _sortingOptions = listOf(
        SelectionOption("latest", false),
        SelectionOption("relevant", true)
    ).toMutableStateList()
    val sortingOptions: List<SelectionOption>
        get() = _sortingOptions


    private val _orientationOptions = listOf(
        SelectionOption("landscape", false),
        SelectionOption("portrait", false),
        SelectionOption("squarish", false)
    ).toMutableStateList()
    val orientationOptions: List<SelectionOption>
        get() = _orientationOptions


    private val _colorOptions = listOf(
        SelectionOption("black_and_white", false),
        SelectionOption("black", false),
        SelectionOption("white", false),
        SelectionOption("yellow", false),
        SelectionOption("orange", false),
        SelectionOption("red", false),
        SelectionOption("purple", false),
        SelectionOption("magenta", false),
        SelectionOption("green", false),
        SelectionOption("teal", false),
        SelectionOption("blue", false),
    ).toMutableStateList()
    val colorOptions: List<SelectionOption>
        get() = _colorOptions


    private val _safeOptions = listOf(
        SelectionOption("low", true),
        SelectionOption("high", false)
    ).toMutableStateList()
    val safeOptions: List<SelectionOption>
        get() = _safeOptions


    fun sortingOptionSelected(selectedOption: SelectionOption) {
        _sortingOptions.forEach { it.selected = false }
        _sortingOptions.forEach {
            if (it.option == selectedOption.option) {
                it.selected = true
            }

        }
        _orderBy.value = selectedOption.option
        Log.e("Color filter", _orderBy.value)

    }


    fun orientationOptionSelected(selectedOption: SelectionOption) {
        _orientationOptions.forEach {
            it.selected = it.option == selectedOption.option && !it.selected
        }
        val selectedColorOption = _orientationOptions.find { it.selected }
        _orientation.value = selectedColorOption?.option ?: ""
    }


    fun colorOptionSelected(selectedOption: SelectionOption) {
        _colorOptions.forEach {
            it.selected = it.option == selectedOption.option && !it.selected
        }
        val selectedColorOption = _colorOptions.find { it.selected }
        _color.value = selectedColorOption?.option ?: ""

        Log.e("Color filter", _color.value)
    }

    fun safeOptionSelected(selectedOption: SelectionOption) {
        _safeOptions.forEach { it.selected = false }
        _safeOptions.forEach {
            if (it.option == selectedOption.option) {
                it.selected = true
            }

        }
        _safeSearch.value = selectedOption.option
        Log.e("Color filter", _safeSearch.value)
    }
}

