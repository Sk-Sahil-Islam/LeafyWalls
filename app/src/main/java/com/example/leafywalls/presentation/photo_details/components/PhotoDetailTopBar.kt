package com.example.leafywalls.presentation.photo_details.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.leafywalls.common.isDarkOrLight
import com.example.leafywalls.ui.theme.OnSurfaceDark
import com.example.leafywalls.ui.theme.OnSurfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailTopBar(
    onBackClick: () -> Unit,
    onInfoClick: () -> Unit,
    isDarkOrLight: String
) {

    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "back",
                    tint = when (isDarkOrLight) {
                        "Dark" -> OnSurfaceDark
                        "Light" -> OnSurfaceLight
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        },
        actions = {
            IconButton(onClick = { onInfoClick() }) {
                Icon(
                    modifier = Modifier.size(27.dp),
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    tint = when (isDarkOrLight) {
                        "Dark" -> OnSurfaceDark
                        "Light" -> OnSurfaceLight
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}