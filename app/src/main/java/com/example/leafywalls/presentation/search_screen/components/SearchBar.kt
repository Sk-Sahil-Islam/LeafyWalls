package com.example.leafywalls.presentation.search_screen.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.ui.theme.Sarala
import com.example.leafywalls.ui.theme.SearchBarDark
import com.example.leafywalls.ui.theme.SearchBarLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    singleLined: Boolean = true,
    isFocused: MutableState<Boolean>,
    onFocusChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {

    val clipAnim = remember { Animatable(initialValue = 100f) }
    val paddingHorizontal = remember { Animatable(initialValue = 1f) }


    LaunchedEffect(isFocused.value) {
        if (isFocused.value) {
            launch {
                clipAnim.animateTo(
                    targetValue = 0f, animationSpec = tween(durationMillis = 500)
                )
            }
            launch {
                paddingHorizontal.animateTo(
                    targetValue = 0f, animationSpec = tween(durationMillis = 500)
                )
            }
        } else {
            launch {
                clipAnim.animateTo(
                    targetValue = 100f, animationSpec = tween(durationMillis = 500)
                )
            }
            launch {
                paddingHorizontal.animateTo(
                    targetValue = 1f, animationSpec = tween(durationMillis = 500)
                )

            }
        }
    }

    Box(modifier = modifier) {

        val interactionSource = remember { MutableInteractionSource() }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            BasicTextField(
                value = value,

                onValueChange = onValueChange,
                textStyle = TextStyle(
                    fontFamily = Sarala,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = (24 * paddingHorizontal.value).dp)
                    .onFocusChanged {
                        isFocused.value = it.hasFocus
                        onFocusChange(it.hasFocus)
                    }
                    .clip(RoundedCornerShape(clipAnim.value))
                    .height(55.dp),
                interactionSource = interactionSource,
                enabled = enabled,
                singleLine = singleLined,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch() })
            ) {
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = it,
                    enabled = enabled,
                    singleLine = singleLined,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    placeholder = {
                        Text(
                            text = "Search",
                            style = TextStyle.Default.copy(
                                fontFamily = Sarala,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                            )
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onSearch()

                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "search",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Unspecified,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(0.8f),
                        unfocusedContainerColor = if (isSystemInDarkTheme()) SearchBarDark else SearchBarLight,
                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                        top = 0.dp,
                        bottom = 0.dp,
                    )
                )
            }
        }
    }
}