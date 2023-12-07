package com.example.leafywalls.presentation.photo_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailTopBar(
    onBackClick: () -> Unit,
    onInfoClick: () -> Unit
) {

    TopAppBar(
        modifier = Modifier.padding(horizontal = 10.dp),
        title = {},
        navigationIcon = {
            InfoIcon(
                icon = Icons.AutoMirrored.Rounded.ArrowBack,
                modifier = Modifier.size(30.dp)
            ) {
                onBackClick()
            }
        },
        actions = {
            InfoIcon(
                icon = Icons.Outlined.Info,
            ) {
                onInfoClick()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}

@Composable
fun InfoIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))

    ) {
        Icon(
            modifier = modifier
                .clickable { onClick() }
                .padding(4.dp),
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}