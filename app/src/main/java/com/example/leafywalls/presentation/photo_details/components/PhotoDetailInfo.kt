package com.example.leafywalls.presentation.photo_details.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.R
import com.example.leafywalls.common.toColor
import com.example.leafywalls.common.toDate
import com.example.leafywalls.domain.model.PhotoDetail
import com.example.leafywalls.ui.theme.AmaticSC
import com.example.leafywalls.ui.theme.DateIconColor
import com.example.leafywalls.ui.theme.LocationIconColor
import com.example.leafywalls.ui.theme.Sarala


@OptIn(ExperimentalLayoutApi::class)
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
                modifier = Modifier.offset(y = (-18).dp),
                text = title.replaceFirstChar { it.uppercase() },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AmaticSC,
                color = MaterialTheme.colorScheme.onSurface
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "location",
                    tint = LocationIconColor
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = location,
                    fontFamily = Sarala,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.size(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_ic),
                    contentDescription = "date",
                    tint = DateIconColor
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = createdAt.toDate(),
                    fontFamily = Sarala,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.size(18.dp))


            ColorRow(hexCode = color, color = color.toColor())


            Spacer(modifier = Modifier.size(22.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                tags.forEach { tag ->
                    TagComposable(tag = tag)
                }
            }
        }
    }
}