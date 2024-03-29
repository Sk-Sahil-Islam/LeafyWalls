package com.example.leafywalls.domain.repository

import com.example.leafywalls.data.db.Favorite
import com.example.leafywalls.data.remote.dto.PhotoDetailDto
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.data.remote.dto.SearchDto
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getPhotos(page: Int): List<PhotoDto>

    suspend fun getPhotoDetail(photoId: String): PhotoDetailDto

    suspend fun getPopularPhotos(page: Int): List<PhotoDto>

    suspend fun getRandomPhoto(): PhotoDto

    suspend fun getSearchedPhotos(
        page: Int,
        query: String,
        orderBy: String?,
        safeSearch: String?,
        color: String?,
        orientation: String?
    ): SearchDto

    fun getFavourite(): Flow<List<Favorite>>

    suspend fun insertFavourite(favorite: Favorite)

    suspend fun deleteFavourites(favorite: Favorite)

    suspend fun deleteMultipleFavourite(favorites: List<Favorite>)
}