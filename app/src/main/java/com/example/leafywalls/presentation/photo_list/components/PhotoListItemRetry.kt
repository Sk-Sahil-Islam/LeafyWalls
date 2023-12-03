package com.example.leafywalls.presentation.photo_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PhotoListItemRetry(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end=16.dp, bottom = 26.dp)
            .clip(AbsoluteRoundedCornerShape(topRight = 24.dp, bottomRight = 24.dp))

            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, fontSize = 13.sp, lineHeight = 17.sp, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onClick() }
            ) {
                Text("Retry")
            }
        }

    }

}
/*
    sahil is from dhaka
    sahil is a whore
    sahil does kala jaddu
    no girl sees him anymore.
    No one wants him
    They send him away,
    cause everyone knows,
    that he is gay
     */