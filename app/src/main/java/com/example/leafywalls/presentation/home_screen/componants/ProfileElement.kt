package com.example.leafywalls.presentation.home_screen.componants

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.leafywalls.R
import com.example.leafywalls.domain.model.UserData
import com.example.leafywalls.ui.theme.Sarala

@Composable
fun ProfileElement(
    modifier: Modifier = Modifier,
    userData: UserData? = null,
    onLoginClick: () -> Unit,
    onProfilePicClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(top = 7.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfilePhotoNavigation(
                    userData = userData,
                    onProfilePicClick = {
                        onProfilePicClick()
                    }
                )
                if (userData != null) {

                    Spacer(modifier = Modifier.size(20.dp))
                    Text(
                        text = (if (userData.userName?.isNotBlank() == true) userData.userName else "Anonymous"),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Sarala
                    )
                }
            }
            if (userData == null) {
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedButton(
                    onClick = { onLoginClick() },
                    border = BorderStroke(
                        1.dp,
                        SolidColor(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    )
                ) {
                    Text(text = "Login", fontFamily = Sarala, fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(modifier = Modifier.size(35.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onBackground.copy(0.1f))
            )
        }
    }
}

@Composable
fun ProfilePhotoNavigation(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    userData: UserData? = null,
    onProfilePicClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)

    ) {
        if (userData?.profilePictureUrl != null && userData.profilePictureUrl != "null") {
            AsyncImage(
                modifier = modifier
                    .fillMaxSize()
                    .clickable { onProfilePicClick() },
                model = userData.profilePictureUrl,
                placeholder = painterResource(id = R.drawable.person_ic),
                contentDescription = "profile",
                contentScale = ContentScale.Crop
            )
        } else {
            if (userData != null) {

                Image(
                    modifier = modifier
                        .fillMaxSize()
                        .clickable { onProfilePicClick() },
                    painter = painterResource(id = R.drawable.person_ic),
                    contentDescription = "profile"
                )
            } else {
                Image(
                    modifier = modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.person_ic),
                    contentDescription = "profile"
                )
            }

        }
    }
}