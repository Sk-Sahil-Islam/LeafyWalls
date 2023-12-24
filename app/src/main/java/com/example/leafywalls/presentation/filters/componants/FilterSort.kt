package com.example.leafywalls.presentation.filters.componants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.leafywalls.presentation.search_screen.SearchScreenViewModel
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun FilterSort(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            viewModel.sortingOptions.forEach { option ->
                FilterContainer(
                    selectionOption = option,
                    onClick = { viewModel.sortingOptionSelected(option) }
                )
            }
        }
    }
}