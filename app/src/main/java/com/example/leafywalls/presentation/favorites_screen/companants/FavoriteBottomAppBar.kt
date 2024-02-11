package com.example.leafywalls.presentation.favorites_screen.companants

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.leafywalls.common.shareListToText

@Composable
fun FavoriteBottomAppBar(
    modifier: Modifier = Modifier,
    shareList: Array<String>,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val text = shareListToText(shareList)
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = {
                if (shareList.isNotEmpty())
                    startActivity(context, shareIntent, null)
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Share,
                contentDescription = "share"
            )
        }
        IconButton(onClick = { onDelete() }) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "delete"
            )
        }
    }
}