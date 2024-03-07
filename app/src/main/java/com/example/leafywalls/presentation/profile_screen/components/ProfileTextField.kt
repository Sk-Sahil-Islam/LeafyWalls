package com.example.leafywalls.presentation.profile_screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun UserNameTextField(
    modifier: Modifier = Modifier,
    name: String?,
    isError: Boolean,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = name ?: "Anonymous",
        onValueChange = {
            onValueChange(it)
        },
        isError = isError,
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

//@Composable
//fun BioTextField(
//    modifier: Modifier = Modifier,
//    bio: String,
//    isError: Boolean,
//    onValueChange: (String) -> Unit,
//) {
//    TextField(
//        modifier = modifier,
//        value = bio,
//        onValueChange = {
//            onValueChange(it)
//        },
//        isError = isError,
//        placeholder = {
//            Text(
//                text = "Bio",
//                fontFamily = Sarala
//            )
//        },
//        label = {
//            Text(
//                text = "Bio",
//                fontFamily = Sarala
//            )
//        },
//        trailingIcon = {
//            Icon(
//                imageVector = Icons.Outlined.Edit,
//                contentDescription = "edit bio"
//            )
//        },
//        colors = TextFieldDefaults.colors(
//            focusedContainerColor = Color.Transparent,
//            unfocusedContainerColor = Color.Transparent,
//            errorContainerColor = Color.Transparent,
//            disabledContainerColor = Color.Transparent,
//            unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
//            focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
//            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
//            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
//        )
//    )
//}