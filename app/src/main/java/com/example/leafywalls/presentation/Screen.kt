package com.example.leafywalls.presentation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home_screen")
    object ExploreList: Screen("explore_list")
    object PhotoDetailScreen: Screen("photo_detail_screen")
}
