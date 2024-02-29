package com.example.leafywalls.presentation.login_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.presentation.login_screen.componants.ConnectRow
import com.example.leafywalls.presentation.login_screen.componants.OrText


@Composable
fun LoginSection() {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = {
//            signInViewModel.onEvent(SignUpUiEvent.EmailChange(it))
                    email = it
                },
                //isError = !signInViewModel.signInUiState.value.emailError,
                label = { Text(text = "Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "email"
                    )
                },
                maxLines = 1,
                singleLine = true,
                supportingText = {
//            if (!signInViewModel.signInUiState.value.emailError)
//                Text(text = "Invalid email address.", color = MaterialTheme.colorScheme.error)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedPrefixColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedSuffixColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
//            signInViewModel.onEvent(SignUpUiEvent.PasswordChange(it))
                    password = it
                },
                label = { Text(text = "Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "password"
                    )
                },
                maxLines = 1,
                singleLine = true,
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Outlined.AccountBox
                    else Icons.Outlined.AccountBox

                    val description = if (passwordVisible) "Hide password"
                    else "show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedPrefixColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedSuffixColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.size(16.dp))

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
                Text(text = "LOGIN", fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.size(10.dp))
            
            OrText(color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

            Spacer(modifier = Modifier.size(10.dp))

            ConnectRow(
                onGoogleClick = { /*TODO*/ },
                onFacebookClick = { /*TODO*/ },
                onTwitterClick = {}
            )

        }
    }
}