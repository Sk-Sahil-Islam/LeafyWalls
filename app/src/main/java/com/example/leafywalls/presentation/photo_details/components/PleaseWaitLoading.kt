package com.example.leafywalls.presentation.photo_details.components

import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.R
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun PleaseWaitLoading(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .height(90.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 10.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Column {
                Text(
                    text = stringResource(id = R.string.please_wait),
                    fontFamily = Sarala,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.5.sp
                )
                Text(
                    text = text,
                    fontFamily = Sarala,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.5.sp
                )
            }
            Spacer(modifier = Modifier.size(10.dp))

            LeafyWallsLoading()
        }
    }
}

@Composable
fun LeafyWallsLoading(
    modifier: Modifier = Modifier
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val yOffset by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = -30f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, 100, EaseInCirc),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.leafywalls_ic),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .graphicsLayer(translationY = yOffset)
        )
    }
}

//@Composable
//fun LoadingAnimation() {
//
//    val infiniteTransition = rememberInfiniteTransition(label = "")
//    val yOffset by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = -30f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(600, 100, LinearOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ), label = ""
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Icon(
//            imageVector = Icons.Default.Search,
//            contentDescription = null,
//            modifier = Modifier
//                .size(48.dp)
//                .clip(CircleShape)
//                .padding(16.dp)
//                .graphicsLayer(translationY = yOffset)
//        )
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun LoadingAnimationPreview() {
//    LeafyWallsTheme {
//        LoadingAnimation()
//    }
//}