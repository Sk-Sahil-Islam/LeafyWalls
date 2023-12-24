package com.example.leafywalls.presentation.search_screen


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.leafywalls.R
import com.example.leafywalls.common.clearFocusOnKeyboardDismiss
import com.example.leafywalls.presentation.filters.FilterScreen
import com.example.leafywalls.presentation.search_screen.components.SearchBar
import com.example.leafywalls.presentation.search_screen.components.SearchList


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    var searchBarHeight by remember { mutableStateOf(0.dp) }
    var currentPageIndex by rememberSaveable { mutableStateOf(0) }

    val historyState by viewModel.state.collectAsState()
    var text by remember { historyState.query }
    Scaffold(
        topBar = {
            if(currentPageIndex==0) {
                SearchBar(
                    modifier = Modifier
                        .zIndex(1f)
                        .clearFocusOnKeyboardDismiss(),
                    value = historyState.query.value,
                    onValueChange = {
                        text = it
                        historyState.query.value = it
                    },
                    onSearch = {
                        if (currentPageIndex==0 && text.isNotBlank()) {
                            viewModel.onEvent(SearchEvent.OnSearch(text))
                            currentPageIndex = 1
                        }
                    },
                    paddingValue = { paddingValues ->
                        searchBarHeight = paddingValues
                    },
                    onClickRow = {
                        text = it
                        historyState.query.value = it
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (currentPageIndex==0 && text.isNotBlank()) {
                        viewModel.onEvent(SearchEvent.OnSearch(text))
                        currentPageIndex = 1
                    }
                    else{
                        currentPageIndex = 0
                    }
                }
            ) {
                if(currentPageIndex==0) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search"
                    )
                }
                else {
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
                .background(MaterialTheme.colorScheme.background)

        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                when (currentPageIndex) {
                    0 -> {
                        Spacer(modifier = Modifier.height(searchBarHeight))
                        FilterScreen()
                    }
                    1 -> {
                        SearchList(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

class SelectionOption(val option: String, private var initialSelectedValue: Boolean) {
    var selected by mutableStateOf(initialSelectedValue)
}