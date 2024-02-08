package com.example.leafywalls.presentation.favorites_screen

import com.example.leafywalls.data.db.Favorite

data class FavoriteState(
    val favorites: List<Favorite> = emptyList()
)