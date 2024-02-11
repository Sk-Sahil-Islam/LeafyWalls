package com.example.leafywalls.presentation.favorites_screen.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.leafywalls.R
import com.example.leafywalls.presentation.photo_details.components.CustomDialog
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun ConfirmDeleteDialog(
    showDialog: Boolean,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        CustomDialog(showDialog = showDialog, onDismissRequest = onDismissRequest) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {

                Text(
                    text = stringResource(id = R.string.confirm_delete),
                    fontFamily = Sarala
                )

                Spacer(modifier = Modifier.size(8.dp))

                Row(Modifier.align(Alignment.End)) {
                    TextButton(
                        text = stringResource(id = R.string.cancel),
                        onClick = onCancel
                    )
                    TextButton(
                        text = stringResource(id = R.string.delete),
                        onClick = onDelete
                    )
                }
            }
        }
    }
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(10.dp))
        .clickable { onClick() }
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        Text(
            text = text,
            fontFamily = Sarala,
            fontWeight = FontWeight.Bold
        )
    }
}