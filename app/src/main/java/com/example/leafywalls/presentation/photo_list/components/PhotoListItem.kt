package com.example.leafywalls.presentation.photo_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.leafywalls.domain.model.Photo

@Composable
fun PhotoListItem(
    photo: Photo,
    onClick: (Photo) -> Unit
) {

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(6.dp),
        onClick = {
            onClick(photo)
        }
    ) {

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp-4.dp

        val density = LocalDensity.current
        val widthPx = with(density) {screenWidth.roundToPx()}/2
        val heightPx = with(density) {screenHeight.roundToPx()}/2

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