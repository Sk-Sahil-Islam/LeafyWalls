package com.example.leafywalls.presentation.search_screen

sealed interface SearchEvent1 {

    data class OnSearch(val query: String): SearchEvent1
    data class UpdateQuery(val query: String): SearchEvent1
    data class UpdateSort(val sort: String): SearchEvent1
    data class UpdateOrientation(val orientation: String): SearchEvent1
    data class UpdateSafeSearch(val safeSearch: String): SearchEvent1
    data class UpdateColor(val color: String): SearchEvent1

}