package com.example.leafywalls.common

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewModelScope
import com.example.leafywalls.R
import com.example.leafywalls.presentation.search_screen.SearchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun String.toDate(): String {
    val input = this
    val dateTime = LocalDateTime.parse(input, DateTimeFormatter.ISO_DATE_TIME)

    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return dateTime.format(formatter)
}

fun isDarkOrLight(color: Color): String {
    val luminance = color.luminance()
    Log.e("COLOR HEX2", luminance.toString())
    return if (luminance < 0.5) "Dark"
    else if (luminance > 0.8) "Light"
    else "Neutral"

}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
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

        awaitDispose { viewTreeObserver.removeOnGlobalLayoutListener(listener) }
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

@Composable
fun parseColorImage(colorString: String): Painter {
    return when (colorString) {
        "black" -> painterResource(id = R.drawable.black)
        "white" -> painterResource(id = R.drawable.white)
        "yellow" -> painterResource(id = R.drawable.yellow)
        "orange" -> painterResource(id = R.drawable.orange)
        "red" -> painterResource(id = R.drawable.red)
        "purple" -> painterResource(id = R.drawable.purple)
        "magenta" -> painterResource(id = R.drawable.magenta)
        "green" -> painterResource(id = R.drawable.green)
        "teal" -> painterResource(id = R.drawable.teal)
        "blue" -> painterResource(id = R.drawable.blue)
        else -> painterResource(id = R.drawable.black_white)
    }
}

fun generateRandomColor(): Color {

    val random = Random.Default
    return Color(random.nextInt(256) / 255f, random.nextInt(256) / 255f, random.nextInt(256) / 255f)
}

fun areSearchStatesEqual(state1: SearchState, state2: SearchState): Boolean {
    return state1.isLoading == state2.isLoading &&
            state1.sortOption == state2.sortOption &&
            state1.orientation == state2.orientation &&
            state1.color == state2.color &&
            state1.safeSearch == state2.safeSearch
}

fun shareListToText(shareList: Array<String>): String {
    var result = "Here's my favorite image(s) from LeafyWalls:"
    shareList.forEach {
        result += "\nhttps://unsplash.com/photos/$it"
    }
    return result
}

fun saveBitmapAsJpeg(bitmap: Bitmap, context: Context, fileName: String): String? {
    val folderName = "LeafyWalls"

    val name = "hey.jpg"
    try {
        val folder = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName)

        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file = File(folder, name)

        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.close()
        return file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("PhotoDetailViewModel", e.toString())
    }
    return null
}

fun mSaveMediaToStorage(bitmap: Bitmap?, context: Context) {
    val filename = "${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null

    val folderName = context.getString(R.string.folder_name)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + folderName
                )
            }
            val imageUri: Any? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it as android.net.Uri) }
        }
    } else {
        val imagesDir =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName)
        imagesDir.mkdirs()
        val image = File(imagesDir, filename)
        fos = image.outputStream()
    }

    fos?.use {
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
}

suspend fun triggerDownload(url: String) {
    withContext(Dispatchers.IO) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                Log.e("PhotoDetailViewModel", "Response: $responseBody")
            } else {
                Log.e("PhotoDetailViewModel", "${response.code}")
            }
        } catch (e: IOException) {
            Log.e("PhotoDetailViewModel", "Error: ${e.message}")
        }
    }

}


object Validator {

    private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(email.matches(EMAIL_REGEX.toRegex()))
    }
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(!password.isNullOrEmpty() && password.length >= 6)
    }
}

data class ValidationResult(
    val status: Boolean = false,
    val message: String = ""
)

internal fun isValidPassword(password: String): Boolean {
    if (password.length < 8) return false
    if (password.filter { it.isDigit() }.firstOrNull() == null) return false
    if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
    if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
    if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

    return true
}

private const val ERR_LEN = "Minimum 8 character required."