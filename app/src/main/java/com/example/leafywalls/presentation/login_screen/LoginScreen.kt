package com.example.leafywalls.presentation.login_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.leafywalls.presentation.login_screen.componants.InfoFieldBox
import com.example.leafywalls.presentation.login_screen.componants.LogoBox
import com.example.leafywalls.ui.theme.LeafyWallsTheme

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                scale(scaleX = 1f, scaleY = 1.2f) {
                    drawCircle(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFBFF8E),
                                //Color(0xFFDDFF63),
                                Color(0xFFB9FF63),
                            )
                        ),
                        radius = size.width / 1f,
                        center = Offset(x = size.width / 5f, y = size.height / 3f)
                    )
                }
            },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoBox()
            InfoFieldBox(
                modifier = Modifier
                    .padding(16.dp)
                    .offset(y=(-16).dp)
                    .border(
                        2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFBFF8E).copy(alpha = .8f),
                                Color(0xFFB9FF63).copy(alpha = .8f),
                            )
                        ),
                        RoundedCornerShape(24.dp)
                    )
                    .clip(RoundedCornerShape(24.dp))
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LeafyWallsTheme {
        LoginScreen()
    }
}