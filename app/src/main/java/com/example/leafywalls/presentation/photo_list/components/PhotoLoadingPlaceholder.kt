package com.example.leafywalls.presentation.photo_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.leafywalls.R

@Composable
fun PhotoLoadingPlaceholder(
    width: Dp,
    height: Dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
            .background(Color.Red)
    ) {
        Image(
            modifier = Modifier.fillMaxSize()
                .height(height-4.dp)
                .width((width)),
            painter =
            if (!isSystemInDarkTheme())
                painterResource(id = R.drawable.placeholder_light)
            else painterResource(id = R.drawable.placeholder_dark),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}