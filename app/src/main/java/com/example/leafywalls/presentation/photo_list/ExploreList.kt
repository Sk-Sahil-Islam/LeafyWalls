package com.example.leafywalls.presentation.photo_list

import android.util.Log
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.leafywalls.data.remote.dto.toPhoto
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.home_screen.componants.ErrorImage
import com.example.leafywalls.presentation.photo_list.components.ItemLoadingIndicator
import com.example.leafywalls.presentation.photo_list.components.PhotoListItem
import com.example.leafywalls.presentation.photo_list.components.PhotoListItemRetry
import java.net.UnknownHostException

@Composable
fun ExploreList(
    navController: NavController,
    viewModel: ExploreListViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsLazyPagingItems()
//    val errorMessage = remember {
//        mutableStateOf("")
//    }
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val lifeCycleOwner = LocalLifecycleOwner.current

        state.apply {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(7.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier
                    .padding(horizontal = 8.dp),
            ) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Spacer(modifier = Modifier.size(0.dp))
                }

                items(itemCount) { index ->

                    Row(modifier = Modifier.fillMaxWidth()) {

                        state[index]?.let { photoDto ->
                            PhotoListItem(
                                photo = photoDto.toPhoto(),
                                onClick = {
                                    if (loadState.append !is LoadState.Error) {
                                        val currentState = lifeCycleOwner.lifecycle.currentState
                                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                            navController
                                                .navigate(Screen.PhotoDetailScreen.route + "/${it.photoId}")
                                        }
                                    }
                                },
//                                error = {
//                                    errorMessage.value = it
//                                }
                            )
                        }
                    }
                }

                if (loadState.append is LoadState.Loading) {
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        ItemLoadingIndicator()
                    }
                }

            }

            //Log.e("ExploreList previous", (loadState.append is LoadState.Error).toString())

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomStart),
                visible = loadState.append is LoadState.Error,
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
                if (loadState.append is LoadState.Error) {
                    val e = (loadState.append as LoadState.Error).error
                    if (e is UnknownHostException) {
                        message = "No internet.\nCheck your connection"
                    } else if (e is Exception) {
                        message = e.message ?: "Unknown error occurred"
                    }
                }
                PhotoListItemRetry(
                    text = message.ifEmpty { "Unknown error occurred" }
                ) {
                    this@apply.retry()
                }

            }

            when (loadState.refresh) {

                is LoadState.Error -> {

                    ErrorImage(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = { this.retry() }
                    )
                }

                LoadState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                else -> {}
            }
        }
    }

}