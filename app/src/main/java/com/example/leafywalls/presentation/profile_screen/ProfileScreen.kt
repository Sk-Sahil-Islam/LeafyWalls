package com.example.leafywalls.presentation.profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.leafywalls.domain.model.UserData
import com.example.leafywalls.presentation.home_screen.componants.ProfileCircle
import com.example.leafywalls.presentation.profile_screen.components.ProfileAppBar
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userData: UserData? = null,
    navController: NavController
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    var text by remember {
        mutableStateOf(userData?.userName?.ifBlank { "Anonymous" })
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            ProfileAppBar(
                onBackClick = {
                    val currentState = lifeCycleOwner.lifecycle.currentState
                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(24.dp))

            ProfileCircle(
                size = 100.dp,
                onProfilePicClick = {

                }
            )
            Spacer(modifier = Modifier.size(24.dp))
            TextField(
                value = text ?: "Anonymous",
                onValueChange = {
                    text = it
                },
                placeholder = {
                    Text(
                        text = "Name",
                        fontFamily = Sarala
                    )
                },
                label = {
                    Text(
                        text = "Name",
                        fontFamily = Sarala
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "edit name"
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

        }
    }
}
