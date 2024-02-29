package com.example.leafywalls.presentation.login_screen.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leafywalls.R
import com.example.leafywalls.ui.theme.BackgroundDark
import com.example.leafywalls.ui.theme.BackgroundLight
import com.example.leafywalls.ui.theme.Sarala
import com.example.leafywalls.ui.theme.Smooch

@Composable
fun LogoBox(
    modifier: Modifier = Modifier
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.leafywalls_ic),
                contentDescription = "logo",
                Modifier.size(150.dp)
                    .offset(y=10.dp)
            )
            Text(
                modifier = Modifier.offset(y=(-16).dp),
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.SemiBold,
                fontFamily = Smooch,
                fontSize = 36.sp,
                color = BackgroundDark.copy(alpha = 0.9f)
            )
        }
    }
}