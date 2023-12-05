package com.example.leafywalls.presentation.photo_details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.leafywalls.common.isDarkOrLight
import com.example.leafywalls.common.toColor
import com.example.leafywalls.presentation.photo_details.components.LoadingDetail
import com.example.leafywalls.presentation.photo_details.components.PhotoDetailInfo
import com.example.leafywalls.presentation.photo_details.components.PhotoDetailTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun PhotoDetailScreen(
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }


    state.photo?.let {
        val colorHex = it.color
        val isDarkOrLight = isDarkOrLight(colorHex.toColor())

        Scaffold (
            topBar = {
                PhotoDetailTopBar(
                    onBackClick = {},
                    onInfoClick = {isSheetOpen = true},
                    isDarkOrLight = isDarkOrLight
                )
            }
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                state.photo.apply {

                    val configuration = LocalConfiguration.current
                    val screenWidth = configuration.screenWidthDp.dp
                    val screenHeight = configuration.screenHeightDp.dp-4.dp

                    val density = LocalDensity.current
                    val widthPx = with(density) {screenWidth.roundToPx()}
                    val heightPx = with(density) {screenHeight.roundToPx()}

                    val photoUrl = "$url&w=$widthPx&h=$heightPx&fit=crop&crop=entropy"


                    SubcomposeAsyncImage(
                        modifier=Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photoUrl)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        loading = { LoadingDetail() }
                    )


                    if(isSheetOpen) {
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
            }
        }
    }


}