package com.example.leafywalls.presentation.home_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leafywalls.presentation.home_screen.componants.HomeScreenTopBar
import com.example.leafywalls.presentation.photo_list.ExploreList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    Scaffold(
        topBar = {
            HomeScreenTopBar(
                onMenuClick = {},
                onSearchClick = {}
            )
        }
    ) {

        val titles = listOf("Explore", "Collections", "Popular", "Topics")

        var selectedIndex by remember { mutableIntStateOf(0) }

        val pagerState = rememberPagerState { titles.size }
        val scope = rememberCoroutineScope()

//        LaunchedEffect(key1 = selectedIndex) {
//            pagerState.animateScrollToPage(selectedIndex)
//        }
//        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
//            if(!pagerState.isScrollInProgress) {
//                selectedIndex = pagerState.currentPage
//            }
//        }

        Column(modifier = Modifier.padding(it)) {
            PrimaryScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp,
                containerColor = MaterialTheme.colorScheme.background,
                divider = { HorizontalDivider(color = DividerDefaults.color.copy(alpha = 0.25f)) }

            ) {
                titles.forEachIndexed{ index, title ->
                    Tab(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selected = selectedIndex == index,
                        //onClick = { selectedIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
            
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->

                when(index) {
                    0 -> {
                        ExploreList(
                            navController = navController
                        )
                    }
                    else -> {}

                }
            }
        }
    }


}