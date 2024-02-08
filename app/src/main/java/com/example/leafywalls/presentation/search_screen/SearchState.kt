package com.example.leafywalls.presentation.search_screen

data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val sortOption: String = "",
    val orientation: String = "",
    val color: String = "",
    val safeSearch: String = ""
)