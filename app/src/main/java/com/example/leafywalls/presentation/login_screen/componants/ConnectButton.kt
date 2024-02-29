package com.example.leafywalls.presentation.login_screen.componants

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.leafywalls.R
import com.example.leafywalls.ui.theme.BackgroundLight


@Composable
fun ConnectRow(
    modifier: Modifier = Modifier,
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onTwitterClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(26.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConnectButton(
            image = painterResource(id = R.drawable.ic_google),
            onClick = onGoogleClick
        )
        ConnectButton(
            image = painterResource(id = R.drawable.ic_facebook),
            onClick = onFacebookClick
        )
        ConnectButton(
            image = painterResource(id = R.drawable.ic_twitter),
            onClick = onTwitterClick,
            color = if (isSystemInDarkTheme()) BackgroundLight else Color.Unspecified
        )
    }
}

@Composable
fun ConnectButton(
    image: Painter,
    onClick: () -> Unit,
    color: Color = Color.Unspecified
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = image,
            tint = color,
            contentDescription = null
        )
    }
}