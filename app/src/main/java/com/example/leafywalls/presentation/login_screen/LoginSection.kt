package com.example.leafywalls.presentation.login_screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.leafywalls.common.Resource
import com.example.leafywalls.data.AuthViewModel
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.login_screen.componants.ConnectRow
import com.example.leafywalls.presentation.login_screen.componants.EmailOutlinedTextField
import com.example.leafywalls.presentation.login_screen.componants.OrText
import com.example.leafywalls.presentation.login_screen.componants.PasswordOutfieldTextField1
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch


@Composable
fun LoginSection(
    authViewModel: AuthViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val launcherGoogle =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                authViewModel.googleSignIn(credentials)
            } catch (it: ApiException) {
                print(it)
            }
        }

    val lifeCycleOwner = LocalLifecycleOwner.current
    val loginState by remember { loginViewModel.signInUiState }
    val googleSignInState = authViewModel.googleState.collectAsState()

    val scope = rememberCoroutineScope()
    val state = authViewModel.signInState.collectAsState()
    val context = LocalContext.current

    val isButtonEnabled = remember { mutableStateOf(true) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailOutlinedTextField(
                email = loginState.email,
                isError = !loginViewModel.signInUiState.value.emailError,
                onValueChange = {
                    loginViewModel.onEvent(SignUpUiEvent.EmailChange(it))
                }
            )

            PasswordOutfieldTextField1(
                password = loginState.password,
                onValueChange = {
                    loginViewModel.onEvent(SignUpUiEvent.PasswordChange(it))
                },
                passwordVisible = passwordVisible,
                onVisibilityClick = {
                    passwordVisible = !passwordVisible
                }
            )

            Spacer(modifier = Modifier.size(26.dp))

            Button(
                onClick = {
                    scope.launch {
                        authViewModel.loginUser(loginState.email, loginState.password)
                    }
                },
                enabled = isButtonEnabled.value && loginViewModel.validateAll() && loginState.password.isNotEmpty(),
                modifier = Modifier
                    .widthIn(min = 150.dp)
            ) {
                if (state.value is Resource.Loading || googleSignInState.value is Resource.Loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    isButtonEnabled.value = false
                } else {
                    Text(text = "LOGIN", fontSize = 15.sp)
                }

            }

            Spacer(modifier = Modifier.size(10.dp))

            OrText(color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

            Spacer(modifier = Modifier.size(10.dp))

            ConnectRow(
                onGoogleClick = { authViewModel.googleLaunch(launcher = launcherGoogle) },
                onFacebookClick = { /*TODO*/ },
                onTwitterClick = {}
            )

        }
        state.value?.let {
            when (it) {
                is Resource.Error -> {
                    LaunchedEffect(state.value is Resource.Error) {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        isButtonEnabled.value = true
                    }
                    isButtonEnabled.value = true
                }

                is Resource.Loading -> {}
                is Resource.Success -> {
                    LaunchedEffect(state.value is Resource.Success) {
                        isButtonEnabled.value = true
                        if (authViewModel.currentUser?.isEmailVerified == true) {
                            Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG).show()

                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                navController.navigate(Screen.HomeScreen.route) {
                                    popUpTo(0)
                                }
                            }
                        }
                    }
                }
            }
        }

        googleSignInState.value?.let {
            when (it) {
                is Resource.Error -> {
                    LaunchedEffect(googleSignInState.value is Resource.Error) {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {}
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(
                            context,
                            "Sign in successful ${it.data?.email}",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(0)
                            }
                        }
                    }
                }
            }
        }
    }
}