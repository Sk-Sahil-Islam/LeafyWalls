package com.example.leafywalls.presentation.home_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.leafywalls.R
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.favorites_screen.FavoriteViewModel
import com.example.leafywalls.presentation.favorites_screen.FavouriteScreen
import com.example.leafywalls.presentation.favorites_screen.MultiSelectTopBar
import com.example.leafywalls.presentation.favorites_screen.componants.ConfirmDeleteDialog
import com.example.leafywalls.presentation.favorites_screen.componants.FavoriteBottomAppBar
import com.example.leafywalls.presentation.home_screen.componants.HomeScreenTopBar
import com.example.leafywalls.presentation.home_screen.componants.NavigationItems
import com.example.leafywalls.presentation.photo_list.ExploreList
import com.example.leafywalls.presentation.popular_photo_list.PopularList
import com.example.leafywalls.ui.theme.Sarala
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    var isConfirmDialog by remember { mutableStateOf(false) }
    val favoriteState by favoriteViewModel.state.collectAsState()
    val lifeCycleOwner = LocalLifecycleOwner.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val localDensity = LocalDensity.current
    var heightIs by remember {
        mutableStateOf(0.dp)
    }

    val items = listOf(
        NavigationItems(
            route = Screen.HomeScreen.route,
            title = "Home",
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Default.Home
        ),
        NavigationItems(
            route = Screen.PremiumScreen.route,
            title = "Premium",
            unselectedIcon = painterResource(id = R.drawable.premium_outlined),
            selectedIcon = painterResource(id = R.drawable.premium_filled)
        ),
        NavigationItems(
            route = Screen.SettingsScreen.route,
            title = "Settings",
            unselectedIcon = Icons.Outlined.Settings,
            selectedIcon = Icons.Default.Settings
        ),
//        NavigationItems(
//            route = Screen.PhotoDetailScreen.route,
//            title = "✨ Surprise me ✨",
//            unselectedIcon = Icons.Outlined.ShoppingCart,
//            selectedIcon = Icons.Default.ShoppingCart
//        )
    )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.75f),
                drawerShape = RectangleShape,
                drawerContainerColor = MaterialTheme.colorScheme.background
            ) {
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                fontFamily = Sarala,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }

//                            navController.navigate(item.route) {
//                                popUpTo(navController.graph.findStartDestination().id)
//                                launchSingleTop = true
//                            }
                        },
                        icon = {
                            if (item.selectedIcon1 is ImageVector) {

                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon1
                                    } else item.unselectedIcon1 as ImageVector,
                                    contentDescription = item.title
                                )
                            } else {
                                Icon(
                                    painter = if (index == selectedItemIndex) {
                                        item.selectedIcon1 as Painter
                                    } else item.unselectedIcon1 as Painter,
                                    contentDescription = item.title
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent,
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                            unselectedIconColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "✨ Surprise me ✨",
                            fontFamily = Sarala,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    selected = selectedItemIndex == items.size,
                    onClick = {
                        selectedItemIndex = items.size
                        scope.launch {
                            drawerState.close()
                        }
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            navController
                                .navigate(Screen.RandomScreen.route)
                        }
                    },
//                    icon = {
//                        Icon(
//                            painter = if (selectedItemIndex == items.size) {
//                                painterResource(id = R.drawable.giftcard_outlined)
//                            } else painterResource(id = R.drawable.giftcard_outlined),
//                            contentDescription = null
//                        )
//                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.Transparent,
                        unselectedContainerColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                        unselectedIconColor = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Help",
                            fontFamily = Sarala,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    selected = selectedItemIndex == items.size + 1,
                    onClick = {
                        selectedItemIndex = items.size + 1
                        scope.launch {
                            drawerState.close()
                        }
//                        val currentState = lifeCycleOwner.lifecycle.currentState
//                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
//                            navController
//                                .navigate(Screen.RandomScreen.route)
//                        }
                    },
                    icon = {
                        Icon(
                            painter = if (selectedItemIndex == items.size + 1) {
                                painterResource(id = R.drawable.help_filled)
                            } else painterResource(id = R.drawable.help_outlined),
                            contentDescription = "help"
                        )
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.Transparent,
                        unselectedContainerColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                        unselectedIconColor = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        },
        drawerState = drawerState
    ) {

        Scaffold(
            topBar = {
                HomeScreenTopBar(
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onSearchClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            navController
                                .navigate(Screen.SearchScreen.route)
                        }
                    }
                )

            },
            bottomBar = {
                if (favoriteState.isMultiSelect) {
                    FavoriteBottomAppBar(
                        shareList =favoriteState.favorites.filter { it.isSelected }.map { it.item.photoId }.toTypedArray(),
                        onDelete = {
                            isConfirmDialog = true
                            //favoriteViewModel.deleteSelected()
                        }
                    )
                }
            }
        ) { paddingValues ->

            val titles = listOf("Explore", "Categories", "Popular", "Favorites")

            var selectedIndex by remember { viewModel.selectedIndex }

            val pagerState = rememberPagerState { titles.size }

            LaunchedEffect(key1 = selectedIndex) {
                pagerState.animateScrollToPage(selectedIndex)
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if (!pagerState.isScrollInProgress) {
                    selectedIndex = pagerState.currentPage
                    viewModel.updateIndex(selectedIndex)
                }
            }

            Column(modifier = Modifier.padding(paddingValues)) {

                if (!favoriteState.isMultiSelect) {

                    PrimaryScrollableTabRow(
                        modifier = Modifier.onGloballyPositioned { coordinates ->
                            heightIs = with(localDensity) { coordinates.size.height.toDp() }

                        },
                        selectedTabIndex = pagerState.currentPage,
                        edgePadding = 0.dp,
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
                                        fontFamily = Sarala,
                                        color = if (index == selectedIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            )
                        }
                    }
                } else {
                    MultiSelectTopBar(
                        modifier = Modifier.height(heightIs),
                        isAllSelect = favoriteState.isAllSelect,
                        onCloseClick = {
                            favoriteViewModel.deselectAll()
                            favoriteViewModel.isMultiSelectChange(false)
                        },
                        onSelectAllClick = {
                            if (favoriteState.isAllSelect) {
                                favoriteViewModel.deselectAll()
                            } else {
                                favoriteViewModel.selectAll()
                            }
                        }
                    )
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { index ->

                    when (index) {
                        0 -> {
                            ExploreList(
                                navController = navController
                            )
                        }

                        2 -> {
                            PopularList(navController = navController)
                        }

                        3 -> {
                            FavouriteScreen(navController = navController)
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    ConfirmDeleteDialog(
        showDialog = isConfirmDialog,
        onDismissRequest = { isConfirmDialog = false },
        onDelete = { favoriteViewModel.deleteSelected() },
        onCancel = { isConfirmDialog = false }
    )

    if (drawerState.isOpen) {
        BackHandler {
            scope.launch {
                drawerState.close()
            }
        }
    }

    if (favoriteState.isDeleting) {
        BackHandler {

        }
    }
}