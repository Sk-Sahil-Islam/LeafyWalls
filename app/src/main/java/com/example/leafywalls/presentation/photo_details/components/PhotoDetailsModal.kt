package com.example.leafywalls.presentation.photo_details.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.domain.model.PhotoDetail


@Composable
fun PhotoDetailInfo(
    photoDetail: PhotoDetail
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        photoDetail.apply {

            Text(text = title.replaceFirstChar { it.uppercase() }, fontSize = 18.sp)

            description?.let{Text(text = it)}

            Text(text = location)

            Text(text = createdAt)
        }
    }
}