package com.example.leafywalls.presentation.filters.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun FilterContainer(
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
                MaterialTheme.colorScheme.onPrimaryContainer
        )
    }

}