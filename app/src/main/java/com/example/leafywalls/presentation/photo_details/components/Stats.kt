package com.example.leafywalls.presentation.photo_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.R
import com.example.leafywalls.common.formatNumber
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun Stats(
    modifier: Modifier = Modifier,
    likes: Long,
    downloads: Long,
    views: Long
) {
    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            StatItem(
                painter = painterResource(id = R.drawable.visibility_ic),
                stat = formatNumber(views),
                isClickable = false
            ) {}


            StatItem(
                icon = Icons.Rounded.FavoriteBorder,
                stat = formatNumber(likes)
            ) {

            }

            StatItem(
                painter = painterResource(id = R.drawable.download_ic),
                stat = formatNumber(downloads)
            ) {

            }
            Spacer(modifier = Modifier.size(30.dp))
        }
    }

}

@Composable
fun StatItem(
    icon: ImageVector,
    stat: String,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DetailIcon(icon = icon, modifier = Modifier.size(38.dp), isClickable = isClickable) {
                onClick()
            }
            Text(
                modifier = Modifier.offset(y=(-2).dp),
                text = stat,
                fontFamily = Sarala,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.3.sp,
                style = TextStyle(
                    shadow = Shadow(
                        blurRadius = 2f,
                        color = MaterialTheme.colorScheme.surface
                    )
                )
            )
        }
    }
}

@Composable
fun StatItem(
    painter: Painter,
    stat: String,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DetailIcon(painter = painter, modifier = Modifier.size(38.dp), isClickable = isClickable) {
                onClick()
            }
            Text(
                modifier = Modifier.offset(y=(-2).dp),
                text = stat,
                fontFamily = Sarala,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.3.sp,
                style = TextStyle(
                    shadow = Shadow(
                        blurRadius = 2f,
                        color = MaterialTheme.colorScheme.surface
                    )
                )
            )
        }
    }
}