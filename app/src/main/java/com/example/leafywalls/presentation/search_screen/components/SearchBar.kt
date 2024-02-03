package com.example.leafywalls.presentation.search_screen.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
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
    val clipAnimBottom = remember { Animatable(initialValue = 100f) }
    val paddingHorizontal = remember { Animatable(initialValue = 1f) }

    val heightAnim = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(isFocused.value) {
        if (isFocused.value) {
            launch {
                clipAnim.animateTo(
                    targetValue = 0f, animationSpec = tween(durationMillis = 500)
                )
            }
            launch {
                clipAnimBottom.snapTo(0f)
            }
            launch {
                paddingHorizontal.animateTo(
                    targetValue = 0f, animationSpec = tween(durationMillis = 500)
                )
            }
            launch {
                heightAnim.animateTo(
                    targetValue = 1f, animationSpec = tween(durationMillis = 500)
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
            launch {
                heightAnim.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )
                clipAnimBottom.animateTo(
                    targetValue = 100f, animationSpec = tween(durationMillis = 500)
                )
            }
        }
    }

    Box(modifier = modifier) {

        val interactionSource = remember { MutableInteractionSource() }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

//            TextField(
//                modifier = Modifier
//                    .padding(horizontal = (24 * paddingHorizontal.value).dp)
//                    .fillMaxWidth()
//                    .onFocusChanged {
//                        isFocused.value = it.hasFocus
//                        onFocusChange(it.hasFocus)
//                    }
//                    .clip(RoundedCornerShape(clipAnim.value)),
//                onValueChange = onValueChange,
//                value = value,
//                textStyle = TextStyle.Default.copy(
//                    fontFamily = Sarala,
//                    fontSize = 18.sp,
//                    color = MaterialTheme.colorScheme.onBackground
//                ),
//                enabled = enabled, visualTransformation = VisualTransformation.None, placeholder = {
//                    Text(
//                        text = "Search...", style = TextStyle.Default.copy(
//                            fontFamily = Sarala,
//                            fontSize = 18.sp,
//                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
//                        )
//                    )
//                },
//                trailingIcon = {
//                    IconButton(
//                        onClick = {
//                            onSearch()
//                        }
//                    ) {
//                        Icon(
//                            modifier = Modifier.size(28.dp),
//                            imageVector = Icons.Outlined.Search,
//                            contentDescription = "search",
//                            tint = MaterialTheme.colorScheme.onPrimaryContainer
//                        )
//                    }
//                },
//                colors = TextFieldDefaults.colors(
//                    unfocusedIndicatorColor = Color.Unspecified,
//                    focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(0.8f),
//                    unfocusedContainerColor = if(isSystemInDarkTheme()) SearchBarDark else SearchBarLight,
//                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
//                ),
//                singleLine = singleLined,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//                keyboardActions = KeyboardActions(onSearch = { onSearch() })
//            )

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

//                    .background(
//                        color = MaterialTheme.colorScheme.background,
//                        shape = RoundedCornerShape(clipAnim.value)
//                    )
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
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
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

//@Composable
//fun SearchBarHistory(
//    modifier: Modifier = Modifier,
//    height: Float,
//    horizontalPadding: Dp = 0.dp,
//    viewModel: SearchScreenViewModel = hiltViewModel(),
//    onClick: (String) -> Unit
//) {
//
//    val state = viewModel.state.collectAsState()
//
//    val minHeight = when (state.value.histories.size){
//        0 -> {0}
//        in listOf(1,2,3) -> {
//            (state.value.histories.size * 65) + 62
//        }
//        else ->  {332}
//    }
//
//    val deletedItem = remember {
//        mutableStateListOf<History>()
//    }
//
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = horizontalPadding)
//            .clip(AbsoluteRoundedCornerShape(bottomLeft = 16.dp, bottomRight = 16.dp))
//            .background(MaterialTheme.colorScheme.primaryContainer)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height((minHeight * height).dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            if(state.value.histories.isNotEmpty()) {
//                Spacer(modifier = Modifier.size(3.dp))
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .height(60.dp)
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Recent",
//                        fontFamily = Sarala,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                    IconButton(onClick = { viewModel.onEvent(SearchEvent.ClearHistory) }) {
//
//                        Icon(
//                            painter = painterResource(id = R.drawable.round_clear_all_24),
//                            contentDescription = "clear all"
//                        )
//                    }
//                }
//            }
//
//            LazyColumn(
//                Modifier
//                    .padding(horizontal = 3.dp)
//                    .heightIn(max = 248.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                itemsIndexed(items = state.value.histories, itemContent = { _, item->
//                    AnimatedVisibility(
//                        visible = !deletedItem.contains(item),
//                        exit = slideOutHorizontally(targetOffsetX = { -it }),
//
//                    ) {
//                        val scope = CoroutineScope(Dispatchers.Default)
//                        HistoryRow(
//                            history = item.text,
//                            onDelete = {
//                                scope.launch {
//                                    deletedItem.add(item)
//                                    delay(600)
//                                    viewModel.onEvent(SearchEvent.DeleteHistory(item))
//                                }
//                            },
//                            onClick = {
//                                onClick(it)
//                            }
//                        )
//                    }
//                })
//            }
//            if (state.value.histories.size >= 4) {
//                Icon(
//                    imageVector = Icons.Rounded.KeyboardArrowDown,
//                    contentDescription = "scroll down",
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            }
//            if(state.value.histories.isNotEmpty()) {
//                Spacer(modifier = Modifier.size(2.dp))
//            }
//        }
//    }
//}
//
//@Composable
//fun HistoryRow(
//    modifier: Modifier = Modifier,
//    history: String,
//    onDelete: () -> Unit,
//    onClick: (String) -> Unit
//) {
//
//    val interactionSource = remember { MutableInteractionSource() }
//
//    Row(
//        modifier = modifier
//            .padding(horizontal = 5.dp)
//            .clip(RoundedCornerShape(16.dp))
//            .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(0.05f))
//            .fillMaxWidth()
//            .height(60.dp)
//            .clickable(
//                interactionSource = interactionSource,
//                indication = null,
//                onClick = {
//                    onClick(history)
//                }
//            ),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Spacer(modifier = Modifier.width(10.dp))
//        Text(
//            text = history,
//            fontFamily = Sarala,
//            color = MaterialTheme.colorScheme.onPrimaryContainer
//        )
//        Spacer(modifier = Modifier.weight(1f))
//        IconButton(
//            modifier = Modifier.size(43.dp),
//            onClick = onDelete
//        ) {
//            Icon(
//                imageVector = Icons.Rounded.Close,
//                contentDescription = "delete",
//                tint = MaterialTheme.colorScheme.onPrimaryContainer
//            )
//        }
//    }
//}