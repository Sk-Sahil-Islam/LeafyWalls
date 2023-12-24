package com.example.leafywalls.presentation.search_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.leafywalls.data.db.History
import com.example.leafywalls.presentation.search_screen.SearchEvent
import com.example.leafywalls.presentation.search_screen.SearchScreenViewModel
import com.example.leafywalls.ui.theme.Sarala
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    singleLined: Boolean = true,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    paddingValue: (Dp) -> Unit
) {
    val isFocused = remember { mutableStateOf(false) }

    val clipAnim = remember { Animatable(initialValue = 100f) }
    val clipAnimBottom = remember { Animatable(initialValue = 100f) }
    val paddingHorizontal = remember { Animatable(initialValue = 1f) }
    val density = LocalDensity.current

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

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            TextField(
                modifier = Modifier
                    .padding(horizontal = (24 * paddingHorizontal.value).dp)
                    .fillMaxWidth()
                    .onFocusChanged {
                        isFocused.value = it.hasFocus
                    }
                    .onGloballyPositioned {
                        val textFieldHeight = with(density) {
                            it.size.height.toDp()
                        }
                        paddingValue(textFieldHeight)
                    }
                    .clip(RoundedCornerShape(topStart = clipAnim.value, topEnd = clipAnim.value))
                    .clip(
                        RoundedCornerShape(
                            bottomStart = clipAnimBottom.value, bottomEnd = clipAnimBottom.value
                        )
                    ),
                onValueChange = onValueChange,
                value = value,
                textStyle = TextStyle.Default.copy(
                    fontFamily = Sarala,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                enabled = enabled, visualTransformation = VisualTransformation.None, placeholder = {
                    Text(
                        text = "Search...", style = TextStyle.Default.copy(
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
                    focusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                singleLine = singleLined
            )

            SearchBarHistory(
                modifier = Modifier.zIndex(1f),
                height = heightAnim.value,
                horizontalPadding = (paddingHorizontal.value * 24).dp
            )
        }
    }
}

@Composable
fun SearchBarHistory(
    modifier: Modifier = Modifier,
    height: Float,
    horizontalPadding: Dp = 0.dp,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {

    val state = viewModel.historyState.collectAsState()

    val minHeight = when (state.value.histories.size){
        0 -> {0}
        in listOf(1,2,3) -> {
            (state.value.histories.size * 65)
        }
        else ->  {272}
    }

    val deletedItem = remember {
        mutableStateListOf<History>()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .clip(AbsoluteRoundedCornerShape(bottomLeft = 16.dp, bottomRight = 16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height((minHeight * height).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(state.value.histories.isNotEmpty()) {
                Spacer(modifier = Modifier.size(3.dp))
            }

            LazyColumn(
                Modifier
                    .padding(horizontal = 5.dp)
                    .heightIn(max = 248.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                itemsIndexed(items = state.value.histories, itemContent = { _, item->
                    AnimatedVisibility(
                        visible = !deletedItem.contains(item),
                        exit = slideOutHorizontally(),

                    ) {
                        val scope = CoroutineScope(Dispatchers.Default)
                        HistoryRow(
                            history = item.text,
                            onDelete = {
                                scope.launch {
                                    deletedItem.add(item)
                                    delay(500)
                                    viewModel.onEvent(SearchEvent.DeleteHistory(item))
                                }
                            }
                        )
                    }
                })
            }
            if (state.value.histories.size >= 4) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "scroll down",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            if(state.value.histories.isNotEmpty()) {
                Spacer(modifier = Modifier.size(2.dp))
            }
        }
    }
}

@Composable
fun HistoryRow(
    modifier: Modifier = Modifier,
    history: String,
    onDelete: () -> Unit
) {

    Row(
        modifier = modifier
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(0.05f))
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = history,
            fontFamily = Sarala,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier.size(43.dp),
            onClick = onDelete
        ) {

            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "delete",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}