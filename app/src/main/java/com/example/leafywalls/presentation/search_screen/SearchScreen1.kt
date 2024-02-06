package com.example.leafywalls.presentation.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.rounded.Check
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
import com.example.leafywalls.presentation.search_screen.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen1(
    modifier: Modifier = Modifier,
    viewModel1: SearchScreenViewModel1 = hiltViewModel(),
    navController: NavController
) {
    val newQuery = remember { mutableStateOf("") }

    val lifeCycleOwner = LocalLifecycleOwner.current
    val searchState by viewModel1.searchState.collectAsState()

    var currentPageIndex by rememberSaveable { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf(searchState.query) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isSearchBarFocused = remember { mutableStateOf(false) }

    var isSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val prevState by viewModel1.prevState.collectAsState()

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
                        viewModel1.onEvent(SearchEvent1.UpdateQuery(text))
                    },
                    onSearch = {
                        if (text.isNotBlank()) {
                            viewModel1.onEvent(SearchEvent1.OnSearch)
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
                        viewModel1.onEvent(SearchEvent1.UpdateQuery(text))
                    },
                    onSearch = {
                        if (text.isNotBlank() && (!areSearchStatesEqual(
                                searchState,
                                prevState
                            ) || searchState.query != prevState.query)
                        ) {
                            viewModel1.onEvent(SearchEvent1.OnSearch)
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
                            viewModel1.onEvent(SearchEvent1.ResetToPreviousState)
                        }
                        focusManager.clearFocus()
                        isSheetOpen = false
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier
                            .background(Color.Red)
                    ) {
                        if (!areSearchStatesEqual(searchState, prevState)) {
                            Row {
                                Icon(
                                    modifier = Modifier
                                        .clickable {
                                            viewModel1.onEvent(SearchEvent1.ResetToPreviousState)
                                        },
                                    imageVector = Icons.Outlined.Refresh,
                                    contentDescription = "undo"
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    modifier = Modifier
                                        .clickable {
                                            viewModel1.onEvent(SearchEvent1.OnSearch)
                                            isSheetOpen = false
                                        },
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = "apply"
                                )
                            }
                        }
                    }

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
    onBack: () -> Unit,
    onFilter: () -> Unit
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
                        .size(38.dp) ,
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
                onFocusChange = onFocusChange
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