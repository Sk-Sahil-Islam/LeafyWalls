package com.example.leafywalls.domain.repository

import com.example.leafywalls.data.db.History
import com.example.leafywalls.data.remote.dto.PhotoDetailDto
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.data.remote.dto.SearchDto
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getPhotos(page: Int): List<PhotoDto>

    suspend fun getPhotoDetail(photoId: String): PhotoDetailDto

    suspend fun getPopularPhotos(page: Int): List<PhotoDto>

    suspend fun getSearchedPhotos(
        page: Int,
        query: String,
        orderBy: String,
        safeSearch: String,
        color: String?,
        orientation: String?
    ): SearchDto

    fun getHistory(): Flow<List<History>>

    suspend fun insertHistory(history: History)

    suspend fun deleteHistory(history: History)

    suspend fun clearHistory()
}