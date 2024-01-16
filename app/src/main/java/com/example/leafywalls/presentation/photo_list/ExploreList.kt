package com.example.leafywalls.presentation.photo_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExploreList(
    navController: NavController,
    viewModel: PhotoListViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsLazyPagingItems()

    Column(Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            val pagerState = rememberPagerState(pageCount = { 10 })

//            val fling = PagerDefaults.flingBehavior(
//                state = pagerState,
//                pagerSnapDistance = PagerSnapDistance.atMost(10)
//            )

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
                                    }
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


                when (loadState.append) {

                    is LoadState.Error -> {
                        val e = (loadState.append as LoadState.Error).error
                        var message = ""
                        if (e is UnknownHostException) {
                            message = "No internet.\nCheck your connection"
                        } else if (e is Exception) {
                            message = e.message ?: "Unknown error occurred"
                        }

                        PhotoListItemRetry(
                            modifier = Modifier
                                .align(Alignment.BottomCenter),
                            text = message
                        ) {
                            this.retry()
                        }
                    }

                    else -> {}
                }


                when (loadState.refresh) {

                    is LoadState.Error -> {
                        PhotoListItemRetry(
                            modifier = Modifier
                                .align(Alignment.BottomCenter),
                            text = "No internet.\n" +
                                    "Check your connection"
                        ) {
                            this.retry()
                        }

                        ErrorImage()
                    }

                    LoadState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    else -> {}
                }
            }
        }
    }
}