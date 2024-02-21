package com.example.leafywalls.domain.use_case

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import com.example.leafywalls.R
import com.example.leafywalls.common.Resource
import com.example.leafywalls.common.mSaveMediaToStorage
import com.example.leafywalls.common.saveBitmapAsJpeg
import com.example.leafywalls.common.triggerDownload
import com.example.leafywalls.data.remote.dto.toPhotoDetail
import com.example.leafywalls.domain.model.PhotoDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class SetWallpaperUseCase {

    fun invoke(url: String, context: Context, fileName: String, which: Int, downloadUrl: String): Flow<Resource<String>> = channelFlow {
        val wallpaperManager = WallpaperManager.getInstance(context)

        try {
            trySend(Resource.Loading())

            val task = async(Dispatchers.IO) {
                BitmapFactory.decodeStream(
                    URL(url).openConnection().getInputStream()
                )
            }
            val bitmap = task.await()

            triggerDownload(downloadUrl)

            setWallpaper(
                bitmap = bitmap,
                which = which,
                wallpaperManager
            )

            mSaveMediaToStorage(bitmap, context)

            trySend(Resource.Success(context.getString(R.string.wallpaper_set_successfully)))

        } catch (e: HttpException) {
            trySend(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            trySend(Resource.Error("Couldn't reach server. Check your internet connection"))
        } finally {
            close()
        }
    }
}

private fun setWallpaper(
    bitmap: Bitmap,
    which: Int,
    wallpaperManager: WallpaperManager
) {
    when(which) {
        WallpaperManager.FLAG_LOCK -> {
            wallpaperManager.setBitmap(
                bitmap,
                null,
                false,
                WallpaperManager.FLAG_LOCK
            )
        }
        WallpaperManager.FLAG_SYSTEM -> {
            wallpaperManager.setBitmap(
                bitmap,
                null,
                false,
                WallpaperManager.FLAG_SYSTEM
            )
        }
        else -> { wallpaperManager.setBitmap(bitmap) }
    }

}