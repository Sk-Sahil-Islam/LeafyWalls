package com.example.leafywalls.common

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toDate(): String {
    val input = this
    val dateTime = LocalDateTime.parse(input, DateTimeFormatter.ISO_DATE_TIME)

    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return dateTime.format(formatter)
}

fun isDarkOrLight(color: Color):String {
    val luminance = color.luminance()
    Log.e("COLOR HEX2", luminance.toString())
    return if (luminance < 0.5) "Dark"
    else if (luminance > 0.8) "Light"
    else "Neutral"

}

fun String.toColor(): Color {
    return  Color(android.graphics.Color.parseColor(this))
}