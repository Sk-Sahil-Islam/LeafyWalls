package com.example.leafywalls.presentation

sealed class Screen(val route: String){
    object PhotoListScreen: Screen("photo_list_screen")
    object PhotoDetailScreen: Screen("photo_detail_screen")
}
