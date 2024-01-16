package com.example.leafywalls.presentation.random_photo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.leafywalls.common.Resource
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.photo_details.PhotoDetailViewModel
import com.example.leafywalls.presentation.photo_details.components.DetailIcon
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun RandomScreen(
    viewModel: RandomPhotoViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState(initial = Resource.Loading())
    val lifeCycleOwner = LocalLifecycleOwner.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TopRow(modifier = Modifier.align(Alignment.TopStart)) {
            val currentState = lifeCycleOwner.lifecycle.currentState
            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                navController.popBackStack()
            }
        }

        when(state) {
            is Resource.Error -> {
                Text(
                    text = state.message.toString(),
                    color = MaterialTheme.colorScheme.error,
                    fontFamily = Sarala,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success -> {
                navController.navigate(Screen.PhotoDetailScreen.route + "/${state.data!!.id}") {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
fun TopRow(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        DetailIcon(
            icon = Icons.AutoMirrored.Rounded.ArrowBack,
            modifier = Modifier.size(36.dp)
        ) {
            onBackClick()
        }
    }
}