package com.example.leafywalls.presentation.login_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun OrText(
    modifier: Modifier = Modifier,
    color: Color
) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .size(1.dp)
                    .background(color)
                    .weight(1f)
            )
            Text(
                text = "or",
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                color = color,
                fontFamily = Sarala,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .size(1.dp)
                    .weight(1f)
                    .background(color)
            )

        }
        Text(
            text = "Connect using different methods",
            fontFamily = Sarala,
            fontSize = 12.sp,
            color = color,
            textAlign = TextAlign.Center
        )
    }

}