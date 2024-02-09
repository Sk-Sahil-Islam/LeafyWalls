package com.example.leafywalls.presentation.search_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.leafywalls.R
import com.example.leafywalls.data.remote.dto.toPhoto
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.home_screen.componants.ErrorImage
import com.example.leafywalls.presentation.photo_list.components.ItemLoadingIndicator
import com.example.leafywalls.presentation.photo_list.components.PhotoListItem
import com.example.leafywalls.presentation.photo_list.components.PhotoListItemRetry
import com.example.leafywalls.ui.theme.Sarala
import java.net.UnknownHostException

@Composable
fun SearchList(
    navController: NavController,
    viewModel1: SearchScreenViewModel = hiltViewModel(),
    query: String
) {

    val photos = viewModel1.photos.value.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {

        val lifeCycleOwner = LocalLifecycleOwner.current

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(7.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            modifier = Modifier
                .padding(8.dp)
        ) {

            items(photos.itemCount) { index ->

                Row {

                    photos[index]?.let { photoDto ->
                        PhotoListItem(
                            photo = photoDto.toPhoto(),
                            onClick = {
                                if (photos.loadState.append !is LoadState.Error) {
                                    val currentState = lifeCycleOwner.lifecycle.currentState
                                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                        navController
                                            .navigate(Screen.PhotoDetailScreen.route + "/${it.photoId}")
                                    }
                                }
                            }
                        )
                    }
                }
            }

            if (photos.loadState.append is LoadState.Loading) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    ItemLoadingIndicator()
                }
            }

            if (photos.loadState.append == LoadState.NotLoading(endOfPaginationReached = true) &&
                photos.itemCount != 0) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        text = stringResource(id = R.string.listEndText),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Sarala
                    )
                }
            }

        }

        if (
            photos.itemCount == 0 &&
            photos.loadState.refresh !is LoadState.Loading &&
            photos.loadState.refresh !is LoadState.Error
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.no_result),
                    fontFamily = Sarala
                )
            }

        }


        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomStart),
            visible = photos.loadState.append is LoadState.Error,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(500, easing = EaseInOut)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(500, easing = EaseInOutCubic)
            )
        ) {
            var message = ""
            if (photos.loadState.append is LoadState.Error) {
                val e = (photos.loadState.append as LoadState.Error).error
                if (e is UnknownHostException) {
                    message = "No internet.\nCheck your connection"
                } else if (e is Exception) {
                    message = e.message ?: "Unknown error occurred"
                }
            }
            PhotoListItemRetry(
                text = message.ifEmpty { "Unknown error occurred" }
            ) {
                photos.retry()
            }

        }


        when (photos.loadState.refresh) {

            is LoadState.Error -> {

                ErrorImage(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = { photos.retry() }
                )
            }

            LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            else -> {}
        }

    }
}