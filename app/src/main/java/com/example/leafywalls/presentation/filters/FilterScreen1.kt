package com.example.leafywalls.presentation.filters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.leafywalls.common.parseColorImage
import com.example.leafywalls.presentation.search_screen.SearchEvent1
import com.example.leafywalls.presentation.search_screen.SearchScreenViewModel1
import com.example.leafywalls.ui.theme.OnSurfaceDark
import com.example.leafywalls.ui.theme.OnSurfaceLight
import com.example.leafywalls.ui.theme.PrimaryDark
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun FilterScreen1(
    modifier: Modifier = Modifier,
    onUpdateSort: (String) -> Unit,
    onOrientationUpdate: (String) -> Unit,
    onColorUpdate: (String) -> Unit,
    onSafeSearchUpdate: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        FilterSort1(
            onClick = { onUpdateSort(it) }
        )

        FilterOrientation1( onClick = { onOrientationUpdate(it) })

        FilterColor1(onClick = { onColorUpdate(it) })

        FilterSafeSearch1(onClick = { onSafeSearchUpdate(it) })
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSort1(
    modifier: Modifier = Modifier,
    viewModel1: SearchScreenViewModel1 = hiltViewModel(),
    onClick: (String) -> Unit
) {
    val state by viewModel1.searchState.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Sort",
            fontFamily = Sarala,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            SortingOption.values().forEach { option ->
                FilterContainer1(
                    select = state.sortOption == option.value,
                    option = option.value,
                    //onClick = { viewModel1.onEvent(SearchEvent1.UpdateSort(option.value)) }
                    onClick = { onClick(option.value) }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterOrientation1(
    modifier: Modifier = Modifier,
    viewModel1: SearchScreenViewModel1 = hiltViewModel(),
    onClick: (String) -> Unit
) {
    val state by viewModel1.searchState.collectAsState()
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Orientation",
            fontFamily = Sarala,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OrientationOption.values().forEach { option ->
                FilterContainer1(
                    select = state.orientation == option.value,
                    option = option.value,
                    //onClick = { viewModel1.onEvent(SearchEvent1.UpdateOrientation(option.value)) }
                    onClick = { onClick(option.value) }
                )
            }
        }
    }
}

@Composable
fun FilterColor1(
    modifier: Modifier = Modifier,
    viewModel1: SearchScreenViewModel1 = hiltViewModel(),
    onClick: (String) -> Unit
) {
    val state by viewModel1.searchState.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Color",
            fontFamily = Sarala,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(ColorOption.values()){ colorOption->
                FilterColorCircle1(
                    painter = parseColorImage(colorOption.value),
                    onClick = {
                        //viewModel1.onEvent(SearchEvent1.UpdateColor(colorOption.value))
                         onClick(colorOption.value)
                    },
                    select = colorOption.value == state.color
                )
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSafeSearch1(
    modifier: Modifier = Modifier,
    viewModel1: SearchScreenViewModel1 = hiltViewModel(),
    onClick: (String) -> Unit
) {
    val state by viewModel1.searchState.collectAsState()
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Safe search",
            fontFamily = Sarala,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            SafeSearchOption.values().forEach { option ->
                FilterContainer1(
                    modifier = Modifier.widthIn(min = 100.dp),
                    select = state.safeSearch == option.value,
                    option = option.value,
                    //onClick = { viewModel1.onEvent(SearchEvent1.UpdateSafeSearch(option.value)) }
                    onClick = { onClick(option.value) }
                )
            }
        }
    }
}

@Composable
fun FilterContainer1(
    modifier: Modifier = Modifier,
    select: Boolean,
    option: String,
    onClick: () -> Unit
) {
    val localFocusManager = LocalFocusManager.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable(true,
                onClick = {
                    localFocusManager.clearFocus()
                    onClick()
                })
            .background(
                if (select)
                    MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            )
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(
            text = option,
            fontFamily = Sarala,
            color = if (select)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onBackground
        )
    }

}

@Composable
fun FilterColorCircle1(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit,
    select: Boolean,
    borderColor: Color = if (isSystemInDarkTheme()) OnSurfaceDark else OnSurfaceLight
) {
    val localFocusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .border(
                1.dp,
                color = if (!select) borderColor else MaterialTheme.colorScheme.primary,
                CircleShape
            )
            .clip(CircleShape)
            .clickable {
                localFocusManager.clearFocus()
                onClick()
            }
            .size(80.dp),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        if (select) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryDark.copy(alpha = 0.3f))
            )
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = Icons.Rounded.Check,
                contentDescription = "selected",
                tint = OnSurfaceLight
            )
            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = PrimaryDark
            )
        }
    }

}