package com.example.leafywalls.presentation.filters.componants

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.leafywalls.R
import com.example.leafywalls.common.colorParse
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
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onClick: () -> Unit
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

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            options.forEach { colorOption ->
                FilterColorCircle(
                    color = colorParse(colorOption.option),
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
    color: Color,
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
            .size(40.dp)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        if (color == Color.Transparent) {
            Image(
                painter = painterResource(id = R.drawable.black_white),
                contentDescription = null,
            )
        }
        if (selectionOption.selected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.4f))
            )
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