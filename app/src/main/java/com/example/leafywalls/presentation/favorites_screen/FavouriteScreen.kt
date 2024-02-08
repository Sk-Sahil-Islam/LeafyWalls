package com.example.leafywalls.presentation.favorites_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.leafywalls.domain.model.Photo
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.photo_list.components.PhotoListItem

@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState(emptyList())

    Column(Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .weight(1f),
        ) {

            val lifeCycleOwner = LocalLifecycleOwner.current

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(7.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {

                items(state.size) { index ->

                    Row(modifier = Modifier) {

                        PhotoListItem(
                            photo = Photo(photoId = state[index].photoId, state[index].uri, false),
                            onClick = {

                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    navController
                                        .navigate(Screen.PhotoDetailScreen.route + "/${it.photoId}")
                                }

                            }
                        )

                    }
                }
            }
        }

    }
}
