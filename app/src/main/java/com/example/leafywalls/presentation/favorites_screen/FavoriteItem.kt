package com.example.leafywalls.presentation.favorites_screen

import com.example.leafywalls.data.db.Favorite

data class FavoriteItem(
    val item: Favorite,
    var isSelected: Boolean = false
)