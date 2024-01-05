package com.example.leafywalls.data.repository

import android.content.Context
import com.example.leafywalls.R
import com.example.leafywalls.data.db.History
import com.example.leafywalls.data.db.HistoryDao
import com.example.leafywalls.data.remote.UnsplashApi
import com.example.leafywalls.data.remote.dto.PhotoDetailDto
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.data.remote.dto.SearchDto
import com.example.leafywalls.domain.repository.PhotoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: UnsplashApi,
    private val context: Context,
    private val historyDao: HistoryDao
): PhotoRepository {
    override suspend fun getPhotos(page: Int): List<PhotoDto> {
        return api.getPhotos(context.getString(R.string.API_KEY),page = page)
    }

    override suspend fun getPhotoDetail(photoId: String): PhotoDetailDto {
        return api.getPhotoDetails(photoId = photoId, clientId = context.getString(R.string.API_KEY))
    }

    override suspend fun getPopularPhotos(page: Int): List<PhotoDto> {
        return api.getPopularPhotos(page = page, clientId = context.getString(R.string.API_KEY))
    }

    override suspend fun getSearchedPhotos(
        page: Int,
        query: String,
        orderBy: String,
        safeSearch: String,
        color: String?,
        orientation: String?
    ): SearchDto {
        return api.getSearchedPhotos(
            page = page,
            query = query,
            orderBy = orderBy,
            safeSearch = safeSearch,
            color = color,
            orientation = orientation,
            clientId = context.getString(R.string.API_KEY)
        )
    }

    override fun getHistory() = historyDao.getHistory()

    override suspend fun insertHistory(history: History) = historyDao.insertHistory(history)

    override suspend fun deleteHistory(history: History) = historyDao.deleteHistory(history)

    override suspend fun clearHistory() = historyDao.clearHistory()

}