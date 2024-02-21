package com.example.leafywalls.domain.use_case

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import com.example.leafywalls.R
import com.example.leafywalls.common.Resource
import com.example.leafywalls.common.mSaveMediaToStorage
import com.example.leafywalls.common.triggerDownload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import retrofit2.HttpException
import java.io.IOException
import java.net.URL

class DownloadWallpaperUseCase {

    fun invoke(url: String, context: Context, downloadUrl: String): Flow<Resource<String>> = channelFlow {

        try {
            trySend(Resource.Loading())

            val task = async(Dispatchers.IO) {
                BitmapFactory.decodeStream(
                    URL(url).openConnection().getInputStream()
                )
            }
            val bitmap = task.await()

            triggerDownload(downloadUrl)

            mSaveMediaToStorage(bitmap, context)

            trySend(Resource.Success(context.getString(R.string.successful_download_message)))

        } catch (e: HttpException) {
            trySend(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            trySend(Resource.Error("Couldn't reach server. Check your internet connection"))
        } finally {
            close()
        }
    }
}