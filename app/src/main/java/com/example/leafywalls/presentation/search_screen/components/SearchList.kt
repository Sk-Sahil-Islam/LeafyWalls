package com.example.leafywalls.presentation.search_screen.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.leafywalls.R
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.data.remote.dto.toPhoto
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.home_screen.componants.ErrorImage
import com.example.leafywalls.presentation.photo_list.components.ItemLoadingIndicator
import com.example.leafywalls.presentation.photo_list.components.PhotoListItem
import com.example.leafywalls.presentation.photo_list.components.PhotoListItemRetry
import com.example.leafywalls.presentation.popular_photo_list.PopularListViewModel
import com.example.leafywalls.presentation.search_screen.SearchScreenViewModel
import com.example.leafywalls.ui.theme.Sarala
import java.net.UnknownHostException

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchList(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {

    val photos = viewModel.photos.value.collectAsLazyPagingItems()


    Column(Modifier.fillMaxSize()) {

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

                    Row(modifier = Modifier.animateItemPlacement()) {

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

            }

            if (photos.itemCount == 0 && photos.loadState.refresh != LoadState.Loading) {
                Box(
                    Modifier.fillMaxSize()
                        .padding(16.dp)
                ) {

                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.no_result),
                        fontFamily = Sarala
                    )
                }

            }


            when (photos.loadState.append) {

                is LoadState.Error -> {
                    val e = (photos.loadState.append as LoadState.Error).error
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
                        photos.retry()
                    }
                }

                else -> {}
            }


            when (photos.loadState.refresh) {

                is LoadState.Error -> {
                    PhotoListItemRetry(
                        modifier = Modifier
                            .align(Alignment.BottomCenter),
                        text = "No internet.\n" +
                                "Check your connection"
                    ) {
                        photos.retry()
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