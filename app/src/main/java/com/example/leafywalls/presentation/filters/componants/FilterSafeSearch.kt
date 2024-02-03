package com.example.leafywalls.presentation.filters.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.leafywalls.presentation.search_screen.SearchScreenViewModel
import com.example.leafywalls.presentation.search_screen.SelectionOption
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun FilterSafeSearch(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            viewModel.safeOptions.forEach { option ->
                SafeSearchContainer(
                    selectionOption = option,
                    onClick = { viewModel.safeOptionSelected(option) }
                )
            }
        }
    }
}

fun safeSearchParse(option: String): String {
    return when(option) {
        "low" -> "No"
        "high" -> "Yes"
        else -> "Yes"
    }
}

@Composable
fun SafeSearchContainer(
    modifier: Modifier = Modifier,
    selectionOption: SelectionOption,
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
                if (selectionOption.selected)
                    MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .width(60.dp)
    ) {
        Text(
            text = selectionOption.option,
            fontFamily = Sarala,
            color = if (selectionOption.selected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onBackground
        )
    }
}