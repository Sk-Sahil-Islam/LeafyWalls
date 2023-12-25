package com.example.leafywalls.domain.use_case

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.example.leafywalls.common.Resource
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
    operator fun invoke(url: String, context: Context): Flow<Resource<String>> = channelFlow {
        val wallpaperManager = WallpaperManager.getInstance(context)

        try {
            trySend(Resource.Loading())

            val task = async(Dispatchers.IO) {
                BitmapFactory.decodeStream(
                    URL(url).openConnection().getInputStream()
                )
            }

            val bitmap = task.await()
            Log.e("loading for", "loading")
            trySend(Resource.Success("Wallpaper successfully set"))

            wallpaperManager.setBitmap(
                bitmap,
                null,
                false,
                WallpaperManager.FLAG_LOCK
            )

            // Toast.makeText(context, "Wallpaper successfully set", Toast.LENGTH_SHORT).show()

        } catch (e: HttpException) {
            trySend(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            trySend(Resource.Error("Couldn't reach server. Check your internet connection"))
        } finally {
            close()
        }
    }
}