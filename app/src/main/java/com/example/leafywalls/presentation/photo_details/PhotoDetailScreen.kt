package com.example.leafywalls.presentation.photo_details

import android.annotation.SuppressLint
import android.app.WallpaperManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.leafywalls.R
import com.example.leafywalls.data.db.Favorite
import com.example.leafywalls.presentation.favorites_screen.FavoriteViewModel
import com.example.leafywalls.presentation.photo_details.components.CustomDialog
import com.example.leafywalls.presentation.photo_details.components.LoadingDetail
import com.example.leafywalls.presentation.photo_details.components.PhotoDetailInfo
import com.example.leafywalls.presentation.photo_details.components.PhotoDetailTopBar
import com.example.leafywalls.presentation.photo_details.components.SetDialog
import com.example.leafywalls.presentation.photo_details.components.SideBar
import com.example.leafywalls.presentation.photo_details.components.PleaseWaitLoading

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun PhotoDetailScreen(
    viewModel: PhotoDetailViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController
) {
    val favoriteState by favoriteViewModel.state.collectAsState()
    val state = viewModel.state.value

    var isDetailsHidden by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isSetDialog by remember { mutableStateOf(false) }
    var isPhotoLoading by remember { mutableStateOf(true) }
    var isPhotoError by remember { mutableStateOf(false) }

    val lifeCycleOwner = LocalLifecycleOwner.current

    state.photo?.let { photoDetail ->

        val isFavorite =
            mutableStateOf(favoriteState.favorites.any { photoDetail.id == it.item.photoId })

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp - 4.dp

        val density = LocalDensity.current
        val widthPx = with(density) { screenWidth.roundToPx() }
        val heightPx = with(density) { screenHeight.roundToPx() }

        val photoUrl = "${photoDetail.url.raw}&w=$widthPx&h=$heightPx&fit=crop&crop=entropy"
        val downloadUrl = "${photoDetail.url.full}&w=$widthPx&h=$heightPx&fit=crop&crop=entropy"

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (!isPhotoLoading) {
                        isDetailsHidden = !isDetailsHidden
                    }
                },
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
                    loading = {
                        LoadingDetail()
                        isPhotoLoading = true
                        isPhotoError = false
                    },
                    onSuccess = {
                        isPhotoLoading = false
                        isPhotoError = false
                    },
                    onError = {
                        isPhotoError = true
                        isPhotoLoading = false
                    }
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
                PleaseWaitLoading(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (100).dp),
                    text = stringResource(id = R.string.setting_wallpaper)
                )
            }
        }

        if (!isPhotoLoading) {

            Box(modifier = Modifier.fillMaxSize()) {

                val context = LocalContext.current

                AnimatedVisibility(
                    visible = !isDetailsHidden && !isSetDialog,
                    enter = slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(500, easing = EaseInOut)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(500, easing = EaseInOutCubic)
                    )
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
                    visible = !isDetailsHidden && !isSetDialog,
                    enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(500, easing = EaseInOut)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(500, easing = EaseInOutCubic)
                    ),
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    SideBar(
                        isFavorite = isFavorite.value,
                        photoId = photoDetail.id,
                        onSetClick = { isSetDialog = true },
                        onFavoriteClick = {
                            if (!isFavorite.value) {
                                favoriteViewModel.addFavorite(
                                    Favorite(
                                        photoId = photoDetail.id,
                                        uri = photoDetail.url.raw
                                    )
                                )
                                isFavorite.value = true
                            } else {
                                favoriteState.favorites.find { photoDetail.id == it.item.photoId }
                                    ?.let { favoriteViewModel.removeFavorite(it.item) }
                                isFavorite.value = false
                            }
                        }
                    )
                }

                CustomDialog(
                    showDialog = isSetDialog,
                    onDismissRequest = { isSetDialog = false }
                ) {
                    SetDialog(
                        onSetHome = {
                            isSetDialog = false
                            viewModel.setWallpaper(
                                url = photoUrl,
                                downloadUrl = photoDetail.links.download_location + "&client_id=" + context.getString(
                                    R.string.API_KEY
                                ),
                                context = context,
                                fileName = photoDetail.title,
                                which = WallpaperManager.FLAG_SYSTEM
                            )
                        },
                        onSetLock = {
                            isSetDialog = false
                            viewModel.setWallpaper(
                                url = photoUrl,
                                context = context,
                                fileName = photoDetail.title,
                                downloadUrl = photoDetail.links.download_location + "&client_id=" + context.getString(
                                    R.string.API_KEY
                                ),
                                which = WallpaperManager.FLAG_LOCK
                            )
                        },
                        onSetHomeAndLock = {
                            isSetDialog = false
                            viewModel.setWallpaper(
                                url = photoUrl,
                                context = context,
                                fileName = photoDetail.title,
                                downloadUrl = photoDetail.links.download_location + "&client_id=" + context.getString(
                                    R.string.API_KEY
                                ),
                                which = 0
                            )
                        },
                        onDownload = {
                            isSetDialog = false
                            viewModel.downloadWallpaper(
                                url = downloadUrl,
                                downloadUrl = photoDetail.links.download_location + "&client_id=" + context.getString(
                                    R.string.API_KEY
                                ),
                                context = context
                            )
                        },
                        onDismiss = { isSetDialog = false }

                    )
                }


//                AnimatedVisibility(
//                    visible = isSetDialog && !state.settingWallpaper,
//                    enter = fadeIn(),
//                    exit = fadeOut()
//                ) {
//                    SetDialog(
//                        onSetHome = {
//                            isSetDialog = false
//                            viewModel.setWallpaper(
//                                url = photoUrl,
//                                context = context,
//                                which = WallpaperManager.FLAG_SYSTEM
//                            )
//                        },
//                        onSetLock = {
//                            isSetDialog = false
//                            viewModel.setWallpaper(
//                                url = photoUrl,
//                                context = context,
//                                which = WallpaperManager.FLAG_LOCK
//                            )
//                        },
//                        onSetHomeAndLock = {
//                            isSetDialog = false
//                            viewModel.setWallpaper(
//                                url = photoUrl,
//                                context = context,
//                                which = 0
//                            )
//                        },
//                        onDownload = { },
//                        onDismiss = { isSetDialog = false }
//
//                    )
//                }
            }
        }

        if (isSetDialog) {
            BackHandler {
                isSetDialog = false
            }
        }
    }
}