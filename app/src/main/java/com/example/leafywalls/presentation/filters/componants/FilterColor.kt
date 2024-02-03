package com.example.leafywalls.presentation.filters.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.leafywalls.presentation.search_screen.SearchScreenViewModel
import com.example.leafywalls.presentation.search_screen.SelectionOption
import com.example.leafywalls.ui.theme.OnSurfaceDark
import com.example.leafywalls.ui.theme.OnSurfaceLight
import com.example.leafywalls.ui.theme.PrimaryDark
import com.example.leafywalls.ui.theme.Sarala

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterColor(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val options = viewModel.colorOptions


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
            items(options){ colorOption->
                FilterColorCircle(
                    painter = parseColorImage(colorOption.option),
                    onClick = {
                        viewModel.colorOptionSelected(colorOption)
                    },
                    selectionOption = colorOption
                )
            }
        }
    }

}

@Composable
fun FilterColorCircle(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: (SelectionOption) -> Unit,
    selectionOption: SelectionOption,
    borderColor: Color = if (isSystemInDarkTheme()) OnSurfaceDark else OnSurfaceLight
) {
    val localFocusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .border(
                1.dp,
                color = if (!selectionOption.selected) borderColor else MaterialTheme.colorScheme.primary,
                CircleShape
            )
            .clip(CircleShape)
            .clickable {
                localFocusManager.clearFocus()
                onClick(selectionOption)
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
        if (selectionOption.selected) {

            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.Rounded.Check,
                contentDescription = "selected",
                tint = OnSurfaceLight
            )
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Rounded.Check,
                contentDescription = "selected",
                tint = PrimaryDark
            )
        }
    }

}