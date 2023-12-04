package com.example.leafywalls.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leafywalls.presentation.home_screen.componants.HomeScreenTopBar
import com.example.leafywalls.presentation.photo_list.ExploreList

@OptIn(ExperimentalMaterial3Api::class)
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
        var selectedIndex by remember {
            mutableIntStateOf(0)
        }
        val titles = listOf("Explore", "Collections", "Popular", "Topics")

        Column(modifier = Modifier.padding(it)) {
            PrimaryScrollableTabRow(
                selectedTabIndex = selectedIndex,
                edgePadding = 0.dp,
                containerColor = MaterialTheme.colorScheme.background,
                divider = { HorizontalDivider(color = DividerDefaults.color.copy(alpha = 0.35f)) }

            ) {
                titles.forEachIndexed{ index, title ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }

            when(selectedIndex) {
                0 -> {
                    ExploreList(
                        navController = navController
                    )
                }
                else -> {
                    Text(text = "hello")}

            }

        }
    }


}