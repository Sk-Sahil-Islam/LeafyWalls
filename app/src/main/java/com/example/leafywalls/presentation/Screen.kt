package com.example.leafywalls.presentation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home_screen")
    object SearchScreen: Screen("search_screen")
    object FavoriteScreen: Screen("favorite_screen")
    object PhotoDetailScreen: Screen("photo_detail_screen")
    object ProfileScreen: Screen("profile_screen")
    object PremiumScreen: Screen("premium_screen")
    object SettingsScreen: Screen("settings_screen")
    object RandomScreen: Screen("random_screen")
    object HelpScreen: Screen("help_screen")
    object WelcomeScreen: Screen("welcome_screen")
}
