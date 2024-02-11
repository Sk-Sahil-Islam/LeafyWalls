package com.example.leafywalls.presentation.photo_details.components

import android.content.Intent
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.leafywalls.R
import com.example.leafywalls.common.shareListToText
import com.example.leafywalls.ui.theme.FavoriteColor
import com.example.leafywalls.ui.theme.OnSurfaceDark
import kotlinx.coroutines.launch

@Composable
fun SideBar(
    modifier: Modifier = Modifier,
    onSetClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    photoId: String,
    isFavorite: Boolean
) {
    val context = LocalContext.current
    val text = "Check this image out from LeafyWalls:\nhttps://unsplash.com/photos/$photoId"
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.size(250.dp))

            FavoriteIcon(isFavorite = isFavorite) {
                onFavoriteClick()
            }

            DetailIcon(
                painter = painterResource(id = R.drawable.download_ic),
                modifier = Modifier.size(48.dp)
            ) {
                onSetClick()
            }

            DetailIcon(
                icon = Icons.Rounded.Share,
                iconSize = 28.dp
            ) {
                if (photoId.isNotEmpty())
                    ContextCompat.startActivity(context, shareIntent, null)
            }
        }
    }
}

@Composable
fun SetIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        DetailIcon(
            painter = painterResource(id = R.drawable.download_ic),
            modifier = Modifier.size(48.dp)
        ) {
            onClick()
        }
    }

}


@Composable
fun FavoriteIcon(
    isFavorite: Boolean,
    color: Color = if (isFavorite) FavoriteColor else OnSurfaceDark,
    onClick: () -> Unit
) {
    val scaleA = remember { Animatable(initialValue = 1f) }
    val scaleB = remember { Animatable(initialValue = 1f) }
    val scaleC = remember { Animatable(initialValue = 1f) }

    Log.e("PhotoDetailScreen", isFavorite.toString())

    LaunchedEffect(isFavorite) {
        if (isFavorite) {
            launch {
                scaleA.animateTo(
                    targetValue = 0.3f,
                    animationSpec = tween(
                        durationMillis = 50
                    )
                )
                scaleA.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                scaleB.animateTo(
                    targetValue = 0.75f,
                    animationSpec = tween(
                        durationMillis = 50
                    )
                )
                scaleB.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                scaleC.animateTo(
                    targetValue = 0.5f,
                    animationSpec = tween(
                        durationMillis = 50
                    )
                )
                scaleC.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier
                .blur(1.dp)
                .size(48.dp)
                .scale(scaleB.value),
            imageVector = Icons.Rounded.Favorite,
            contentDescription = null,
            tint = Color(0xFF2C2C2C)
        )
        if (isFavorite) {

            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .scale(scaleC.value)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onClick() },
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null,
                tint = Color(0xFF860000)
            )
        }

        Icon(
            modifier = Modifier
                .size(48.dp)
                .scale(scaleA.value)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onClick() },
            imageVector = Icons.Rounded.Favorite,
            contentDescription = "favorite",
            tint = color
        )
    }
}