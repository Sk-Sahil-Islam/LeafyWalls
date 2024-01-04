package com.example.leafywalls.presentation.photo_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun LoadingDetail() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

