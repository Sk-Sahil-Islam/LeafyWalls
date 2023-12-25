package com.example.leafywalls.common

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

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


fun View.isKeyboardOpen(): Boolean {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect);
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom;
    return keypadHeight > screenHeight * 0.15
}
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {

    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }

    if (isFocused) {
        val isKeyboardOpen by rememberIsKeyboardOpen()

        val focusManager = LocalFocusManager.current
        LaunchedEffect(isKeyboardOpen) {
            if (isKeyboardOpen) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}

@Composable
fun rememberIsKeyboardOpen(): State<Boolean> {
    val view = LocalView.current

    return produceState(initialValue = view.isKeyboardOpen()) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener { value = view.isKeyboardOpen() }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        awaitDispose { viewTreeObserver.removeOnGlobalLayoutListener(listener)  }
    }
}


fun formatNumber(number: Long): String {
    return when {
        number >= 1_000_000_000 -> String.format("%.1fb", number / 1_000_000_000.0)
        number >= 1000000 -> String.format("%.1fm", number / 1000000.0)
        number >= 1000 -> String.format("%.1fk", number / 1000.0)
        else -> number.toString()
    }
}

fun colorParse(colorString: String): Color {
    return when(colorString) {
        "black" -> Color.Black
        "white" -> Color.White
        "yellow" -> Color(0xFFFFFF00)
        "orange" -> Color(0xFFFFA500)
        "red" -> Color(0xFFFF0000)
        "purple" -> Color(0xFF800080)
        "magenta" -> Color(0xFFFF00FF)
        "green" -> Color(0xFF00FF00)
        "teal" -> Color(0xFF008080)
        "blue" -> Color(0xFF0000FF)
        else -> {Color.Transparent}
    }
}

fun generateRandomColor(): Color {

    val random = Random.Default
    Log.e("COLOR FILTER", random.nextInt(256).toString())
    return Color(random.nextInt(256)/255f, random.nextInt(256)/255f, random.nextInt(256)/255f)
}