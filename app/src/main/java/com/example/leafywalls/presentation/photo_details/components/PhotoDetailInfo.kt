package com.example.leafywalls.presentation.photo_details.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.R
import com.example.leafywalls.common.toDate
import com.example.leafywalls.domain.model.PhotoDetail
import com.example.leafywalls.ui.theme.AmaticSC
import com.example.leafywalls.ui.theme.Sarala


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

            Text(
                modifier = Modifier.offset(y=(-18).dp),
                text = title.replaceFirstChar { it.uppercase() },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AmaticSC
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "location")
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = location,
                    fontFamily = Sarala
                )
            }

            Spacer(modifier = Modifier.size(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.outline_calendar_today_24 ), contentDescription = "location")
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = createdAt.toDate(),
                    fontFamily = Sarala
                )
            }

        }
    }
}