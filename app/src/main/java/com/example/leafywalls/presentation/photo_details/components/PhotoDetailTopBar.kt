package com.example.leafywalls.presentation.photo_details.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.leafywalls.R

@Composable
fun PhotoDetailTopBar(
    onBackClick: () -> Unit,
    onInfoClick: () -> Unit
) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DetailIcon(
                icon = Icons.AutoMirrored.Rounded.ArrowBack,
                modifier = Modifier.size(36.dp)
            ) {
                onBackClick()
            }

            DetailIcon(
                painter = painterResource(id = R.drawable.sharp_more_horiz_24),
                modifier = Modifier.size(36.dp)
            ) {
                onInfoClick()
            }
        }
    }
}

@Composable
fun DetailIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.60f))
            .clickable(
                indication = if (!isClickable) null else LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
    ) {
        Icon(
            modifier = Modifier
                .padding(5.dp)
                .clip(CircleShape)
                .size(48.dp),
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun DetailIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.60f))
            .clickable(
                indication = if (!isClickable) null else LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
    ) {
        Icon(
            modifier = Modifier
                .padding(5.dp)
                .clip(CircleShape)
                .size(48.dp),
            painter = painter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}