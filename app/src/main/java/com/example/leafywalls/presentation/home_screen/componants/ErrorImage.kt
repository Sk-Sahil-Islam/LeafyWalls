package com.example.leafywalls.presentation.home_screen.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.leafywalls.R
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun ErrorImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
        ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(250.dp),
                painter = painterResource(id = R.drawable.no_connection_bro),
                contentDescription = "error",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = "Seems like you are disconnected",
                fontFamily = Sarala,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                onClick = { onClick() }
            ) {
                Text("Retry")
            }
        }

    }
}