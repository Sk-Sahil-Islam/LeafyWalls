package com.example.leafywalls.presentation.photo_details.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.leafywalls.R
import com.example.leafywalls.ui.theme.FavoriteColor
import com.example.leafywalls.ui.theme.OnSurfaceDark
import com.example.leafywalls.ui.theme.OnSurfaceLight
import kotlinx.coroutines.launch

@Composable
fun Stats(
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.size(200.dp))

            FavoriteIcon(isFavorite = isFavorite) {
                isFavorite = !isFavorite
            }

            DetailIcon(
                painter = painterResource(id = R.drawable.download_ic),
                modifier = Modifier.size(48.dp)
            ) {

            }
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

    Log.e("IS FAVORITE", isFavorite.toString())

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


//@Composable
//fun StatItem(
//    icon: ImageVector,
//    stat: String,
//    isClickable: Boolean = true,
//    onClick: () -> Unit
//) {
//    Box(contentAlignment = Alignment.Center) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            DetailIcon(icon = icon, modifier = Modifier.size(38.dp), isClickable = isClickable) {
//                onClick()
//            }
//            Text(
//                modifier = Modifier.offset(y=(-2).dp),
//                text = stat,
//                fontFamily = Sarala,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 16.3.sp,
//                style = TextStyle(
//                    shadow = Shadow(
//                        blurRadius = 2f,
//                        color = MaterialTheme.colorScheme.surface
//                    )
//                )
//            )
//        }
//    }
//}
//
//@Composable
//fun StatItem(
//    painter: Painter,
//    stat: String,
//    isClickable: Boolean = true,
//    onClick: () -> Unit
//) {
//    Box(contentAlignment = Alignment.Center) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            DetailIcon(painter = painter, modifier = Modifier.size(38.dp), isClickable = isClickable) {
//                onClick()
//            }
//            Text(
//                modifier = Modifier.offset(y=(-2).dp),
//                text = stat,
//                fontFamily = Sarala,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 16.3.sp,
//                style = TextStyle(
//                    shadow = Shadow(
//                        blurRadius = 2f,
//                        color = MaterialTheme.colorScheme.surface
//                    )
//                )
//            )
//        }
//    }
//}