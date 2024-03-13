package com.example.leafywalls.presentation.profile_screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.leafywalls.R
import com.example.leafywalls.common.areUserDataEqual
import com.example.leafywalls.data.AuthViewModel
import com.example.leafywalls.domain.model.UserData
import com.example.leafywalls.presentation.photo_details.components.PleaseWaitLoading
import com.example.leafywalls.presentation.profile_screen.components.ProfileAppBar
import com.example.leafywalls.presentation.profile_screen.components.UserNameTextField
import com.example.leafywalls.presentation.search_screen.FilterIconButton

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userData: UserData? = null,
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize()) {

        LaunchedEffect(Unit) {
            profileViewModel.updateCurrentState(userData ?: UserData())
        }
        val context = LocalContext.current
        val prevState by profileViewModel.prevState.collectAsState()
        val currentState by profileViewModel.currentState.collectAsState()

        val lifeCycleOwner = LocalLifecycleOwner.current
        val currentStateNav = lifeCycleOwner.lifecycle.currentState

        var uri by remember { mutableStateOf(Uri.parse(userData?.profilePictureUrl)) }

        var text by remember { mutableStateOf(userData?.userName?.ifBlank { "Anonymous" }) }

        val launcher = rememberLauncherForActivityResult(contract =
            ActivityResultContracts.GetContent()) {
            if(it != null) {
                uri = it
                profileViewModel.updateCurrentStateUri(it)
            }
        }

        Scaffold(
            modifier = modifier,
            topBar = {
                ProfileAppBar(
                    onBackClick = {
                        if (currentStateNav.isAtLeast(Lifecycle.State.RESUMED)) {
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

                ProfilePhoto(
                    profilePictureUrl = uri,
                    onEditClick = {
                        launcher.launch("image/*")
                    }
                )

                Spacer(modifier = Modifier.size(24.dp))

                UserNameTextField(
                    name = text,
                    isError = false,
                    onValueChange = {
                        text = it
                        profileViewModel.updateCurrentState(currentState.userData.copy(userName = it))
                    }
                )
                if (!areUserDataEqual(prevState.userData, currentState.userData)) {
                    Button(onClick = {
//                        profileViewModel.uploadUserImage(
//                            imageUri = uri,
//                            authViewModel.currentUser!!.uid,
//                            onSuccess = {
//                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//                                if (currentStateNav.isAtLeast(Lifecycle.State.RESUMED)) {
//                                    navController.popBackStack()
//                                }
//                            }
//                        )
                        if (text?.isNotBlank() == true){
                            profileViewModel.updateProfile(context = context,name = text!!, photoUri = Uri.parse(currentState.userData.profilePictureUrl))
                            //if (currentStateNav.isAtLeast(Lifecycle.State.RESUMED)) { navController.popBackStack() }
                        }
                    }) {
                        Text(text = "Click me!")
                    }
                }
            }

            if (currentState.error.isNotBlank()) {
                Toast.makeText(context, currentState.error, Toast.LENGTH_SHORT).show()
            }
        }
        if (currentState.isLoading) {
            PleaseWaitLoading(
                modifier = Modifier.align(Alignment.Center),
                text = "Updating"
            )
            BackHandler {}
        }
    }
}

@Composable
fun ProfilePhoto(
    modifier: Modifier = Modifier,
    profilePictureUrl: Uri? = null,
    onEditClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        if (profilePictureUrl != null) {
            SubcomposeAsyncImage(
                modifier = modifier
                    .size(100.dp)
                    .clip(CircleShape),
                model = profilePictureUrl,
                loading = {
                    painterResource(id = R.drawable.person_ic)
                },
                contentDescription = "profile",
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                modifier = modifier
                    .size(100.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.person_ic),
                contentDescription = "profile",
                contentScale = ContentScale.Crop
            )
        }

        FilterIconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            containerColor = MaterialTheme.colorScheme.primary.copy(0.9f),
            icon = Icons.Rounded.Edit,
            onClick = onEditClick
        )
    }
}
