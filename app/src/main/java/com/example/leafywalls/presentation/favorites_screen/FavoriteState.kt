package com.example.leafywalls.presentation.favorites_screen


data class FavoriteState(
    var favorites: List<FavoriteItem> = emptyList(),
    var isMultiSelect: Boolean = false,
    var isAllSelect: Boolean = false,
    var isDeleting: Boolean = false
)