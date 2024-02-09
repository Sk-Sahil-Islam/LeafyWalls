package com.example.leafywalls.presentation.search_screen

sealed interface SearchEvent {

    object OnSearch: SearchEvent
    data class UpdateQuery(val query: String): SearchEvent
    data class UpdateSort(val sort: String): SearchEvent
    data class UpdateOrientation(val orientation: String): SearchEvent
    data class UpdateSafeSearch(val safeSearch: String): SearchEvent
    data class UpdateColor(val color: String): SearchEvent
    object ResetToPreviousState: SearchEvent

}