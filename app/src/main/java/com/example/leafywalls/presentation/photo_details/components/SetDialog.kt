package com.example.leafywalls.presentation.photo_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.leafywalls.R
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun SetDialog(
    modifier: Modifier = Modifier,
    onSetHome: () -> Unit,
    onSetLock: () -> Unit,
    onSetHomeAndLock: () -> Unit,
    onDownload: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onDismiss
            )
    ) {

        Box(
            modifier = modifier
                .clip(AbsoluteRoundedCornerShape(topLeft = 24.dp, topRight = 24.dp))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {}
                )
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = onDismiss
                            )
                            .size(30.dp),
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "close"
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.size(10.dp))
                SetDialogOptionRow(
                    option = stringResource(id = R.string.set_home),
                    icon = Icons.Outlined.Home,
                    onClick = onSetHome
                )
                SetDialogOptionRow(
                    option = stringResource(id = R.string.set_lock),
                    icon = Icons.Outlined.Lock,
                    onClick = onSetLock
                )
                SetDialogOptionRow(
                    option = stringResource(id = R.string.set_home_lock),
                    icon = Icons.Outlined.AccountBox,
                    onClick = onSetHomeAndLock
                )
                SetDialogOptionRow(
                    option = stringResource(id = R.string.download),
                    icon = painterResource(id = R.drawable.download_ic),
                    onClick = onDownload
                )
            }
        }
    }
}

@Composable
fun SetDialogOptionRow(
    modifier: Modifier = Modifier,
    option: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.3.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary.copy(0.25f))
                ) { onClick() }
                .background(MaterialTheme.colorScheme.onBackground.copy(0.05f))
                .padding(vertical = 16.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = option,
                fontFamily = Sarala
            )


        }
    }
}
@Composable
fun SetDialogOptionRow(
    modifier: Modifier = Modifier,
    option: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.3.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary.copy(0.25f))
                ) { onClick() }
                .background(MaterialTheme.colorScheme.onBackground.copy(0.05f))
                .padding(vertical = 16.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(painter = icon, contentDescription = null, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = option,
                fontFamily = Sarala
            )

        }
    }
}