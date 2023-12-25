package com.example.leafywalls.presentation.photo_details

import android.annotation.SuppressLint
import android.app.WallpaperManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.leafywalls.presentation.photo_details.components.LoadingDetail
import com.example.leafywalls.presentation.photo_details.components.PhotoDetailInfo
import com.example.leafywalls.presentation.photo_details.components.PhotoDetailTopBar
import com.example.leafywalls.presentation.photo_details.components.Stats
import com.example.leafywalls.presentation.photo_details.components.WallpaperSetting

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun PhotoDetailScreen(
    viewModel: PhotoDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value

    var isDetailsHidden by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    val lifeCycleOwner = LocalLifecycleOwner.current


    state.photo?.let {

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp - 4.dp

        val density = LocalDensity.current
        val widthPx = with(density) { screenWidth.roundToPx() }
        val heightPx = with(density) { screenHeight.roundToPx() }

        val photoUrl = "${it.url}&w=$widthPx&h=$heightPx&fit=crop&crop=entropy"

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { isDetailsHidden = !isDetailsHidden },
            contentAlignment = Alignment.Center
        ) {

            state.photo.apply {

                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    loading = { LoadingDetail() }
                )


                if (isSheetOpen) {
                    ModalBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = { isSheetOpen = false },
                        containerColor = MaterialTheme.colorScheme.background
                    ) {
                        PhotoDetailInfo(photoDetail = this@apply)
                    }
                }
            }


            if (state.error.isNotBlank()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }


            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }


            if (state.settingWallpaper) {
                WallpaperSetting(
                    modifier = Modifier.align(Alignment.TopCenter).offset(y= (100).dp)
                )
            }

        }


        Box(modifier = Modifier.fillMaxSize()) {

            val context = LocalContext.current

            AnimatedVisibility(
                visible = !isDetailsHidden,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                PhotoDetailTopBar(
                    onBackClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            navController.popBackStack()
                        }
                    },
                    onInfoClick = { isSheetOpen = true }
                )
            }
            AnimatedVisibility(
                visible = !isDetailsHidden,
                enter = slideInHorizontally(initialOffsetX = {it}),
                exit = slideOutHorizontally(targetOffsetX = {it}),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Stats(
                    onSetClick = {
                        viewModel.setWallpaper(
                            url = photoUrl,
                            context = context
                        )
                    }
                )
            }
        }

    }
}












