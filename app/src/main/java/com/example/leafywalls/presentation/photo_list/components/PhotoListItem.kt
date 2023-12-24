package com.example.leafywalls.presentation.photo_list.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.leafywalls.domain.model.Photo
import com.example.leafywalls.ui.theme.OnPrimaryContainerDark
import com.example.leafywalls.ui.theme.OnPrimaryContainerLight
import com.example.leafywalls.ui.theme.OnSurfaceDark
import com.example.leafywalls.ui.theme.OnSurfaceLight
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun PhotoListItem(
    photo: Photo,
    onClick: (Photo) -> Unit
) {

    Box {
        if (photo.isSponsored) {
            SponsorHeading(
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(6.dp),
            onClick = {
                onClick(photo)
            }
        ) {

            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp
            val screenHeight = configuration.screenHeightDp.dp - 4.dp

            val density = LocalDensity.current
            val widthPx = with(density) { screenWidth.roundToPx() } / 2
            val heightPx = with(density) { screenHeight.roundToPx() } / 2

            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.url + "&w=$widthPx&h=$heightPx&fit=crop&crop=entropy")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                loading = {
                    PhotoLoadingPlaceholder(screenHeight, screenWidth)
                }
            )
        }
    }
}

@Composable
fun SponsorHeading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .fillMaxWidth()
            .zIndex(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.surface.copy(0.8f),
                        Color.Unspecified
                    )
                )
            )
    ) {
        Row(modifier = Modifier.padding(8.dp)){
            Text(
                text = "Sponsored",
                fontFamily = Sarala,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface.copy(0.9f),
                fontSize = 13.sp
            )

        }
    }
}