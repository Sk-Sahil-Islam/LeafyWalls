package com.example.leafywalls.presentation.login_screen

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.leafywalls.common.Resource
import com.example.leafywalls.common.clearFocusOnKeyboardDismiss
import com.example.leafywalls.data.AuthViewModel
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.login_screen.componants.EmailOutlinedTextField
import com.example.leafywalls.presentation.login_screen.componants.PasswordOutfieldTextField1
import com.example.leafywalls.presentation.login_screen.componants.PasswordOutfieldTextField2
import com.example.leafywalls.ui.theme.LinkColorDark
import com.example.leafywalls.ui.theme.LinkColorLight
import com.example.leafywalls.ui.theme.Sarala
import kotlinx.coroutines.launch

@Composable
fun SignUpSection(
    authViewModel: AuthViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController,
    onLinkClick: () -> Unit
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val state = authViewModel.signUpState.collectAsState()
    val loginState by remember { signUpViewModel.signUpUiState }
    val isButtonEnabled = remember { mutableStateOf(true) }
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    Box {
        Column(
            modifier = Modifier.fillMaxWidth().clearFocusOnKeyboardDismiss(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailOutlinedTextField(
                email = loginState.email,
                isError = !loginState.emailError,
                onValueChange = {
                    signUpViewModel.onEvent(SignUpUiEvent.EmailChange(it))
                }
            )

            PasswordOutfieldTextField1(
                password = loginState.password,
                onValueChange = {
                    signUpViewModel.onEvent(SignUpUiEvent.PasswordChange(it))
                },
                passwordVisible = passwordVisible,
                onVisibilityClick = {
                    passwordVisible = !passwordVisible
                }
            )

            Spacer(modifier = Modifier.size(15.dp))


            PasswordOutfieldTextField2(
                password = loginState.confirmPassword,
                onValueChange = {
                    signUpViewModel.onEvent(SignUpUiEvent.PasswordConfirmChange(it))
                }
            )

            Spacer(modifier = Modifier.size(26.dp))


            Button(
                onClick = {
                    scope.launch {
                        authViewModel.registerUser(loginState.email, loginState.password)
                    }
                },
                enabled = isButtonEnabled.value && signUpViewModel.validateAll() && loginState.password.isNotEmpty(),
                modifier = Modifier
                    .widthIn(min = 150.dp)
            ) {
                if (state.value is Resource.Loading){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    isButtonEnabled.value = false
                }
                else {
                    Text(text = "SIGN UP", fontSize = 15.sp)
                }
            }

            Spacer(modifier = Modifier.size(8.dp))

            val signColor = if (isSystemInDarkTheme()) LinkColorLight else LinkColorDark

            val annotatedString = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W300,
                        fontFamily = Sarala
                    )
                ) {
                    append("Already have an account?  ")
                }
                withStyle(
                    SpanStyle(
                        color = signColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Sarala
                    )
                ) {
                    append("Login")
                }
            }
            ClickableText(text = annotatedString, onClick = {
                val start = annotatedString.text.indexOf("Login")
                val end = start + "Login".length
                if (it in start..end) {
                    onLinkClick()
                }
            })

        }

        state.value?.let {
            when (it) {
                is Resource.Error -> {
                    LaunchedEffect(state.value is Resource.Error) {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        isButtonEnabled.value = true
                    }
                    signUpViewModel.resetPassword()
                }

                is Resource.Loading -> {}
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(context, "Check mail to complete sign up", Toast.LENGTH_LONG)
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