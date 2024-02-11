package com.example.leafywalls.presentation.search_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.leafywalls.R
import com.example.leafywalls.common.areSearchStatesEqual
import com.example.leafywalls.common.clearFocusOnKeyboardDismiss
import com.example.leafywalls.presentation.filters.FilterScreen
import com.example.leafywalls.presentation.search_screen.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val newQuery = remember { mutableStateOf("") }

    val lifeCycleOwner = LocalLifecycleOwner.current
    val searchState by viewModel.searchState.collectAsState()

    var currentPageIndex by rememberSaveable { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf(searchState.query) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isSearchBarFocused = remember { mutableStateOf(false) }

    var isSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val prevState by viewModel.prevState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (currentPageIndex == 0) {
                TopAppBarFilterScreen(
                    modifier = Modifier.clearFocusOnKeyboardDismiss(),
                    value = searchState.query,
                    isFocused = isSearchBarFocused,
                    scrollBehavior = scrollBehavior,
                    onFocusChange = {
                        isSearchBarFocused.value = it
                    },
                    onValueChange = {
                        text = it
                        viewModel.onEvent(SearchEvent.UpdateQuery(text))
                    },
                    onSearch = {
                        if (text.isNotBlank()) {
                            viewModel.onEvent(SearchEvent.OnSearch)
                            currentPageIndex = 1
                        }
                        focusManager.clearFocus()
                    },
                    onClear = { viewModel.onEvent(SearchEvent.ClearQuery) }
                )

            } else {
                TopAppBarSearchList(
                    value = searchState.query,
                    isFocused = isSearchBarFocused,
                    scrollBehavior = scrollBehavior,
                    onFocusChange = {
                        isSearchBarFocused.value = it
                    },
                    onValueChange = {
                        text = it
                        viewModel.onEvent(SearchEvent.UpdateQuery(text))
                    },
                    onSearch = {
                        if (text.isNotBlank() && (!areSearchStatesEqual(
                                searchState,
                                prevState
                            ) || searchState.query != prevState.query)
                        ) {
                            viewModel.onEvent(SearchEvent.OnSearch)
                            newQuery.value = text
                        }
                        focusManager.clearFocus()
                    },
                    onBack = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            navController.popBackStack()
                        }
                    },
                    onFilter = {
                        isSheetOpen = true
                    },
                    onCancel = {
                        viewModel.onEvent(SearchEvent.ClearQuery)
                    }
                )
            }
        }
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.Red)
                .background(MaterialTheme.colorScheme.background)

        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                when (currentPageIndex) {
                    0 -> {
                        FilterScreen(
                            onUpdateSort = { sortOption ->
                                viewModel.onEvent(SearchEvent.UpdateSort(sortOption))
                            },
                            onOrientationUpdate = { orientationOption ->
                                viewModel.onEvent(SearchEvent.UpdateOrientation(orientationOption))
                            },
                            onColorUpdate = { colorOption ->
                                viewModel.onEvent(SearchEvent.UpdateColor(colorOption))
                            },
                            onSafeSearchUpdate = { safeSearchOption ->
                                viewModel.onEvent(SearchEvent.UpdateSafeSearch(safeSearchOption))
                            }
                        )
                    }

                    1 -> {
                        SearchList(
                            navController = navController,
                            query = newQuery.value
                        )
                    }
                }
            }
            if (isSheetOpen) {


                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        if (text.isNotBlank() && !areSearchStatesEqual(
                                state1 = searchState,
                                state2 = prevState
                            )
                        ) {
                            viewModel.onEvent(SearchEvent.ResetToPreviousState)
                        }
                        focusManager.clearFocus()
                        isSheetOpen = false
                    },
                    containerColor = MaterialTheme.colorScheme.background,
                    dragHandle = {},
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        this@ModalBottomSheet.AnimatedVisibility(
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.TopEnd),
                            visible = !areSearchStatesEqual(searchState, prevState),
                            exit = fadeOut(animationSpec = tween(250)),
                            enter = fadeIn(animationSpec = tween(250))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            viewModel.onEvent(SearchEvent.ResetToPreviousState)
                                        },
                                    imageVector = Icons.Rounded.Refresh,
                                    contentDescription = "undo",
                                    tint = MaterialTheme.colorScheme.primary.copy(0.9f)
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                FilterIconButton(
                                    containerColor = MaterialTheme.colorScheme.primary.copy(0.9f),
                                    icon = Icons.Rounded.Check,
                                    onClick = {
                                        viewModel.onEvent(SearchEvent.OnSearch)
                                        isSheetOpen = false
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }


                        FilterScreen(
                            onUpdateSort = { sortOption ->
                                viewModel.onEvent(SearchEvent.UpdateSort(sortOption))
                            },
                            onOrientationUpdate = { orientationOption ->
                                viewModel.onEvent(SearchEvent.UpdateOrientation(orientationOption))
                            },
                            onColorUpdate = { colorOption ->
                                viewModel.onEvent(SearchEvent.UpdateColor(colorOption))
                            },
                            onSafeSearchUpdate = { safeSearchOption ->
                                viewModel.onEvent(SearchEvent.UpdateSafeSearch(safeSearchOption))
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarFilterScreen(
    modifier: Modifier = Modifier,
    value: String,
    isFocused: MutableState<Boolean>,
    scrollBehavior: TopAppBarScrollBehavior,
    onFocusChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Column {

                SearchBar(
                    modifier = modifier
                        .clearFocusOnKeyboardDismiss(),
                    value = value,
                    isFocused = isFocused,
                    onValueChange = onValueChange,
                    onSearch = onSearch,
                    onFocusChange = onFocusChange,
                    onCancel = onClear
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSearchList(
    modifier: Modifier = Modifier,
    value: String,
    isFocused: MutableState<Boolean>,
    scrollBehavior: TopAppBarScrollBehavior,
    onFocusChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onBack: () -> Unit,
    onFilter: () -> Unit,
    onCancel: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.padding(start = 5.dp, end = 10.dp),
        navigationIcon = {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .clickable { onBack() }
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(38.dp),
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        title = {
            SearchBar(
                modifier = modifier
                    .clearFocusOnKeyboardDismiss(),
                value = value,
                isFocused = isFocused,
                onValueChange = onValueChange,
                onSearch = onSearch,
                onFocusChange = onFocusChange,
                onCancel = onCancel
            )
        },
        actions = {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .clickable { onFilter() }
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.round_tune_24),
                    contentDescription = "filter",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
    )
}

@Composable
fun FilterIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    containerColor: Color
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .background(containerColor),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            modifier = Modifier
                .size(30.dp),
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.background
        )
    }
}