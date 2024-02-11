package com.example.leafywalls.presentation.favorites_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.leafywalls.R
import com.example.leafywalls.domain.model.Photo
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.favorites_screen.componants.FavoriteItem
import com.example.leafywalls.presentation.photo_list.components.PhotoListItem
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    Box {


        Column(Modifier.fillMaxSize()) {


            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .weight(1f),
            ) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(7.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        Spacer(modifier = Modifier.size(0.dp))
                    }

                    state.apply {

                        items(favorites.size) { index ->


                            var isSelected by mutableStateOf(favorites[index].isSelected)
                            val haptic = LocalHapticFeedback.current
                            FavouritePhotoListItem(
                                navController = navController,
                                isMultiSelect = state.isMultiSelect,
                                item = favorites[index],
                                isSelected = isSelected,
                                onClick = {
                                    viewModel.updateSelected(index)
                                    isSelected = favorites[index].isSelected
                                },
                                onLongClick = {
                                    if (!state.isMultiSelect) {

                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.isMultiSelectChange(true)
                                    }
                                    viewModel.updateSelected(index)
                                    isSelected = favorites[index].isSelected
                                }
                            )

                        }
                    }
                }
            }
        }
        DeletingFavoriteLoading(
            isDeleting = state.isDeleting,
            modifier = Modifier.align(Alignment.Center)
        )

        if (state.isMultiSelect) {
            BackHandler {
                viewModel.deselectAll()
                viewModel.isMultiSelectChange(false)
            }
        }
    }
}

@Composable
fun FavouritePhotoListItem(
    modifier: Modifier = Modifier,
    item: FavoriteItem,
    navController: NavController,
    isSelected: Boolean,
    isMultiSelect: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    Box {

        PhotoListItem(
            modifier = modifier,
            photo = Photo(
                photoId = item.item.photoId,
                item.item.uri,
                false
            ),
            onClick = {
                if (!isMultiSelect) {
                    val currentState = lifeCycleOwner.lifecycle.currentState
                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        navController
                            .navigate(Screen.PhotoDetailScreen.route + "/${it.photoId}")
                    }
                } else {
                    onClick()
                }
            },
            onLongClick = onLongClick
        )
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-10).dp, y = (-10).dp),
            visible = isMultiSelect,
            exit = fadeOut(animationSpec = tween(250)),
            enter = fadeIn(animationSpec = tween(250))
        ) {

            SelectFavoriteImageIcon(
                isSelected = isSelected,
                onClick = onClick
            )
        }
    }
}

@Composable
fun SelectFavoriteImageIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {

        Box(
            modifier = modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .border(
                    2.dp,
                    brush = SolidColor(MaterialTheme.colorScheme.onBackground.copy(0.7f)),
                    RoundedCornerShape(6.dp)
                )
                .clickable { onClick() }
                .background(
                    if (isSelected)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                    else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {

                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}

@Composable
fun DeletingFavoriteLoading(
    modifier: Modifier = Modifier,
    isDeleting: Boolean
) {
    if (isDeleting) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {}
                )
        ) {

            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(150.dp)
                    .height(100.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = modifier.size(16.dp))
                    Text(text = "Please wait...", fontFamily = Sarala, fontWeight = FontWeight.SemiBold)
                }
            }
        }

    }
}

@Composable
fun MultiSelectTopBar(
    modifier: Modifier = Modifier,
    isAllSelect: Boolean,
    onCloseClick: () -> Unit,
    onSelectAllClick: () -> Unit
) {

    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onCloseClick
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "cancel"
            )
        }

        Text(
            text = stringResource(id = R.string.selectItems),
            fontFamily = Sarala,
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        SelectFavoriteImageIcon(
            isSelected = isAllSelect,
            onClick = onSelectAllClick
        )
    }

}