package com.example.leafywalls.presentation.search_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.leafywalls.presentation.filters.FilterScreen1
import com.example.leafywalls.presentation.photo_details.components.DetailIcon
import com.example.leafywalls.presentation.photo_details.components.PhotoDetailInfo
import com.example.leafywalls.presentation.search_screen.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen1(
    modifier: Modifier = Modifier,
    viewModel1: SearchScreenViewModel1 = hiltViewModel(),
    navController: NavController
) {

    val lifeCycleOwner = LocalLifecycleOwner.current

    val searchBarHeight by remember { mutableStateOf(0.dp) }
    var currentPageIndex by rememberSaveable { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isSearchBarFocused = remember { mutableStateOf(false) }

    var isSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val searchState by viewModel1.searchState.collectAsState()
    var prevState = remember { searchState }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (currentPageIndex == 0) {
                TopAppBarFilterScreen(
                    modifier = Modifier.clearFocusOnKeyboardDismiss(),
                    value = text,
                    isFocused = isSearchBarFocused,
                    scrollBehavior = scrollBehavior,
                    onFocusChange = {
                        isSearchBarFocused.value = it
                    },
                    onValueChange = {
                        text = it

                    },
                    onSearch = {
                        if (text.isNotBlank()) {
                            viewModel1.onEvent(SearchEvent1.UpdateQuery(text))
                            viewModel1.onEvent(SearchEvent1.OnSearch(text))
                            currentPageIndex = 1
                        }
                        focusManager.clearFocus()
                    }
                )

            } else {
                TopAppBarSearchList(
                    value = text,
                    isFocused = isSearchBarFocused,
                    scrollBehavior = scrollBehavior,
                    onFocusChange = {
                        isSearchBarFocused.value = it
                    },
                    onValueChange = {
                        text = it

                    },
                    onSearch = {
                        if (text.isNotBlank() && !areSearchStatesEqual(
                                state1 = searchState,
                                state2 = prevState
                            )
                        ) {
                            viewModel1.onEvent(SearchEvent1.UpdateQuery(text))
                            viewModel1.onEvent(SearchEvent1.OnSearch(text))
                            prevState = searchState
                        }
                        focusManager.clearFocus()
                    },
                    onBack = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            navController.popBackStack()
                        }
                    }
                )
            }

        },
        floatingActionButton = {
            if (currentPageIndex != 0) {

                FloatingActionButton(
                    onClick = {
                        isSheetOpen = true
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_tune_24),
                        contentDescription = "filter"
                    )
                }
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
                        Spacer(modifier = Modifier.height(searchBarHeight))
                        FilterScreen1(
                            onUpdateSort = { sortOption ->
                                viewModel1.onEvent(SearchEvent1.UpdateSort(sortOption))
                            },
                            onOrientationUpdate = { orientationOption ->
                                viewModel1.onEvent(SearchEvent1.UpdateOrientation(orientationOption))
                            },
                            onColorUpdate = { colorOption ->
                                viewModel1.onEvent(SearchEvent1.UpdateColor(colorOption))
                            },
                            onSafeSearchUpdate = { safeSearchOption ->
                                viewModel1.onEvent(SearchEvent1.UpdateSafeSearch(safeSearchOption))
                            }
                        )
                    }

                    1 -> {
                        SearchList1(
                            navController = navController
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
                            viewModel1.onEvent(SearchEvent1.UpdateQuery(text))
                            viewModel1.onEvent(SearchEvent1.OnSearch(text))
                            prevState = searchState
                        }
                        focusManager.clearFocus()
                        isSheetOpen = false
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) {

                    FilterScreen1(
                        onUpdateSort = { sortOption ->
                            viewModel1.onEvent(SearchEvent1.UpdateSort(sortOption))
                        },
                        onOrientationUpdate = { orientationOption ->
                            viewModel1.onEvent(SearchEvent1.UpdateOrientation(orientationOption))
                        },
                        onColorUpdate = { colorOption ->
                            viewModel1.onEvent(SearchEvent1.UpdateColor(colorOption))
                        },
                        onSafeSearchUpdate = { safeSearchOption ->
                            viewModel1.onEvent(SearchEvent1.UpdateSafeSearch(safeSearchOption))
                        }
                    )
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
    onSearch: () -> Unit
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
                    onFocusChange = onFocusChange
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
    onBack: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.padding(horizontal = 5.dp),
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .clickable { onBack() }
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)),
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
        },
        title = {
            SearchBar(
                modifier = modifier
                    .padding(start = 10.dp)
                    .clearFocusOnKeyboardDismiss(),
                value = value,
                isFocused = isFocused,
                onValueChange = onValueChange,
                onSearch = onSearch,
                onFocusChange = onFocusChange
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
    )
}