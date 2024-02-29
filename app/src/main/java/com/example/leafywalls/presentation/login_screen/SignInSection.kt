package com.example.leafywalls.presentation.login_screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.presentation.login_screen.componants.EmailOutlinedTextField
import com.example.leafywalls.presentation.login_screen.componants.PasswordOutfieldTextField1
import com.example.leafywalls.presentation.login_screen.componants.PasswordOutfieldTextField2
import com.example.leafywalls.ui.theme.LinkColorDark
import com.example.leafywalls.ui.theme.LinkColorLight
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun SignInSection(
    onLinkClick: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    var isError by rememberSaveable { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailOutlinedTextField(
                email = email,
                isError = isError,
                onValueChange = {
                    email = it
                }
            )

            //Spacer(modifier = Modifier.size(12.dp))

            PasswordOutfieldTextField1(
                password = password,
                onValueChange = {
                    password = it
                },
                passwordVisible = passwordVisible,
                onVisibilityClick = {
                    passwordVisible = !passwordVisible
                }
            )

            Spacer(modifier = Modifier.size(15.dp))


            PasswordOutfieldTextField2(
                password = password,
                onValueChange = {
                    password = it
                }
            )

            Spacer(modifier = Modifier.size(26.dp))


            Button(
                onClick = {
                    /*scope.launch {
                        authViewModel.loginUser(signInState.email, signInState.password)
                    }*/
                },
                //enabled = isButtonEnabled.value && signInViewModel.validateAll() && signInState.password.isNotEmpty(),
                modifier = Modifier
                    .widthIn(min = 150.dp)
            ) {
//                if (state.value is Resource.Loading || googleSignInState.value is Resource.Loading || facebookState.value is Resource.Loading){
//                    CircularProgressIndicator(
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.size(24.dp)
//                    )
//                    isButtonEnabled.value = false
//                }
//                else {
//                    Text(text = "Sign In", fontSize = 15.sp)
//                }
                Text(text = "SIGN IN", fontSize = 15.sp)
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
                    append("Sign In")
                }
            }
            ClickableText(text = annotatedString, onClick = {
                val start = annotatedString.text.indexOf("Sign In")
                val end = start + "SignIn".length
                if (it in start..end) {
                    onLinkClick()
                }
            })

        }
    }
}