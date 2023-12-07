package com.example.leafywalls.presentation.photo_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.leafywalls.common.isDarkOrLight
import com.example.leafywalls.ui.theme.OnSurfaceDark
import com.example.leafywalls.ui.theme.OnSurfaceLight
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun ColorRow(
    modifier: Modifier = Modifier,
    hexCode: String,
    color: Color
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ColorCircle(modifier = modifier, color = color)

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = hexCode,
            fontFamily = Sarala
        )
    }

}

@Composable
fun ColorCircle(
    modifier: Modifier = Modifier,
    color: Color
) {
    val isDarkLight = isDarkOrLight(color)
    Box(
        modifier = modifier
            .border(
                1.dp,
                color = when (isDarkLight) {
                    "Dark" -> OnSurfaceDark
                    "Light" -> OnSurfaceLight
                    else -> MaterialTheme.colorScheme.onSurface
                }.copy(alpha = 0.5f),
                CircleShape
            )
            .clip(CircleShape)
            .size(24.dp)
            .background(color)
    )
}