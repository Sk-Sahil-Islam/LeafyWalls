package com.example.leafywalls.presentation.home_screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.leafywalls.presentation.home_screen.componants.LeafyWallsTopBar
import com.example.leafywalls.presentation.photo_list.ExploreList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    Scaffold(
        topBar = {
            LeafyWallsTopBar(
                onMenuClick = {},
                onSearchClick = {}
            )
        }
    ) {
        ExploreList(
            paddingValues = it,
            navController = navController
        )
    }

}