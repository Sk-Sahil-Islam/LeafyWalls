package com.example.leafywalls.presentation.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.leafywalls.presentation.filters.componants.FilterColor
import com.example.leafywalls.presentation.filters.componants.FilterOrientation
import com.example.leafywalls.presentation.filters.componants.FilterSafeSearch
import com.example.leafywalls.presentation.filters.componants.FilterSort

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        FilterSort(onClick = {})

        FilterOrientation(onClick = {})

        FilterColor(onClick = {})

        FilterSafeSearch(onClick = {})
    }
}

