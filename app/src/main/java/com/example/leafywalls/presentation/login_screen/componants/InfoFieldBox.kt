package com.example.leafywalls.presentation.login_screen.componants

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.leafywalls.presentation.login_screen.LoginSection
import com.example.leafywalls.presentation.login_screen.SignUpSection
import com.example.leafywalls.ui.theme.Sarala

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InfoFieldBox(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onTabClick: (Int) -> Unit,
    onLinkClick: () -> Unit,
    navController: NavController
) {

    val titles = listOf("Login", "Sign Up")

    //var selectedIndex by remember { mutableStateOf(0) }

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
                        onClick = {
                            //selectedIndex = index
                                  onTabClick(index)
                                  },
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

                    this@Column.AnimatedVisibility(
                        visible = selectedIndex == 0,
                        enter = fadeIn(tween(300, 300)),
                        exit = slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(600)
                        ) + fadeOut(tween(400, 200))
                    ) {
                        LoginSection(navController = navController)
                    }

                    this@Column.AnimatedVisibility(
                        visible = selectedIndex == 1,
                        enter = fadeIn(tween(300, 300)),
                        exit = slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(600)
                        ) + fadeOut(tween(400, 200))
                    ) {
                        SignUpSection(
                            navController = navController,
                            onLinkClick = {
                                //selectedIndex = 0
                                onLinkClick()
                            }
                        )
                    }
                }

        }
    }
}