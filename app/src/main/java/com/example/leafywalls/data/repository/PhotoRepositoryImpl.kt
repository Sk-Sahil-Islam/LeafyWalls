package com.example.leafywalls.data.repository

import android.content.Context
import com.example.leafywalls.R
import com.example.leafywalls.data.db.Favorite
import com.example.leafywalls.data.db.FavouriteDao
import com.example.leafywalls.data.remote.UnsplashApi
import com.example.leafywalls.data.remote.dto.PhotoDetailDto
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.data.remote.dto.SearchDto
import com.example.leafywalls.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: UnsplashApi,
    private val context: Context,
    private val favouriteDao: FavouriteDao
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

    override suspend fun getRandomPhoto(): PhotoDto {
        return api.getRandomPhoto(clientId = context.getString(R.string.API_KEY))
    }

    override suspend fun getSearchedPhotos(
        page: Int,
        query: String,
        orderBy: String?,
        safeSearch: String?,
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

    override fun getFavourite() = favouriteDao.getFavourite()

    override suspend fun insertFavourite(favorite: Favorite) = favouriteDao.insertFavourite(favorite)

    override suspend fun deleteFavourite(favorite: Favorite) = favouriteDao.deleteFavourite(favorite)

    override suspend fun clearFavourite() = favouriteDao.clearFavourite()

}