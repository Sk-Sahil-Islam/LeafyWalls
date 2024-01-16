package com.example.leafywalls.presentation.home_screen.componants

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItems(
    val route: String,
    val title: String,
    val unselectedIcon1: Any,
    val selectedIcon1: Any
) {
    constructor(route: String, title: String, unselectedIcon: ImageVector, selectedIcon: ImageVector) : this(route= route, title=title, unselectedIcon1 = unselectedIcon, selectedIcon1=selectedIcon)
    constructor(route: String, title: String, unselectedIcon: Painter, selectedIcon: Painter) : this(route= route, title=title, unselectedIcon1 = unselectedIcon, selectedIcon1=selectedIcon)
}

