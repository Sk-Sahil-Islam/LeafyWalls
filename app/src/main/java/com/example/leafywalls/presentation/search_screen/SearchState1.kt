package com.example.leafywalls.presentation.search_screen

data class SearchState1(
    val query: String = "",
    val isLoading: Boolean = false,
    val sortOption: String = "",
    val orientation: String = "",
    val color: String = "",
    val safeSearch: String = ""
)