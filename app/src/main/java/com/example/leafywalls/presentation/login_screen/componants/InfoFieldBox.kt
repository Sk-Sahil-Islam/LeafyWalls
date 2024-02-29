package com.example.leafywalls.presentation.login_screen.componants

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.presentation.login_screen.LoginSection
import com.example.leafywalls.presentation.login_screen.SignInSection
import com.example.leafywalls.ui.theme.Sarala

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InfoFieldBox(
    modifier: Modifier = Modifier
) {

    val titles = listOf("Login", "Sign Up")

    var selectedIndex by remember { mutableStateOf(0) }

    val pagerState = rememberPagerState { titles.size }
//
//    LaunchedEffect(key1 = selectedIndex) {
//        pagerState.animateScrollToPage(selectedIndex)
//    }
//    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
//        if (!pagerState.isScrollInProgress) {
//            selectedIndex = pagerState.currentPage
//        }
//    }

    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Column {

            PrimaryTabRow(
                selectedTabIndex = selectedIndex,
                containerColor = MaterialTheme.colorScheme.background,
                divider = { HorizontalDivider(color = DividerDefaults.color.copy(alpha = 0.25f)) }

            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Sarala,
                                color = if (index == selectedIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }
            }
                Box(modifier = Modifier.padding(16.dp)) {

                    when (selectedIndex) {
                        0 -> {
                            LoginSection()
                        }

                        1 -> {
                            SignInSection(
                                onLinkClick = { selectedIndex = 0 }
                            )
                        }
                        else -> {

                        }
                    }
                }

        }
    }
}