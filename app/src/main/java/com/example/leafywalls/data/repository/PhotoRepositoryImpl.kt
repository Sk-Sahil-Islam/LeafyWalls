package com.example.leafywalls.data.repository

import android.content.Context
import com.example.leafywalls.R
import com.example.leafywalls.data.remote.UnsplashApi
import com.example.leafywalls.data.remote.dto.PhotoDetailDto
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.domain.repository.PhotoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: UnsplashApi,
    private val context: Context
): PhotoRepository {
    override suspend fun getPhotos(page: Int): List<PhotoDto> {
        return api.getPhotos(context.getString(R.string.API_KEY),page = page)
    }

    override suspend fun getPhotoDetail(photoId: String): PhotoDetailDto {
        return api.getPhotoDetails(photoId = photoId, clientId = context.getString(R.string.API_KEY))
    }
}