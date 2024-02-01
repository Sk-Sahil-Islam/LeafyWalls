package com.example.leafywalls.presentation.photo_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.leafywalls.R
import com.example.leafywalls.domain.model.Photo
import com.example.leafywalls.ui.theme.PrimaryDark
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
            modifier = Modifier
                .clickable { onClick(photo) },
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {

            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp
            val screenHeight = configuration.screenHeightDp.dp

            val density = LocalDensity.current
            val widthPx = with(density) { screenWidth.roundToPx() } / 2
            val heightPx = with(density) { screenHeight.roundToPx() } / 2

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .height((screenHeight) / 2)
                    .width(screenWidth / 2),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.url + "&w=$widthPx&h=$heightPx&fit=crop&crop=entropy")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = {
                    LoadingPhoto()
                },
                error = {
                    ErrorText(
                        modifier = Modifier.fillMaxSize(),
                        stringResource(id = R.string.errorText)
                    )
                }
            )
        }
    }
}

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    error: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.no_internet_ic),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = error,
                fontFamily = Sarala,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

        }

    }
}

@Composable
fun LoadingPhoto(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(60.dp),
            painter = painterResource(id = R.drawable.ic_image_placeholder),
            contentDescription = null
        )
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
        Row(modifier = Modifier.padding(8.dp)) {
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