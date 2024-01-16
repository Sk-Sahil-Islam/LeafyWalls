package com.example.leafywalls.data.remote

import com.example.leafywalls.data.remote.dto.PhotoDetailDto
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.data.remote.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("/photos")
    suspend fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("page") page: Int
    ): List<PhotoDto>

    @GET("/photos/{photoId}")
    suspend fun getPhotoDetails(
        @Path("photoId") photoId: String,
        @Query("client_id") clientId: String
    ): PhotoDetailDto

    @GET("/photos")
    suspend fun getPopularPhotos(
        @Query("page") page: Int,
        @Query("order_by") orderBy: String = "popular",
        @Query("client_id") clientId: String
    ): List<PhotoDto>

    @GET("/photos/random")
    suspend fun getRandomPhoto(
        @Query("client_id") clientId: String
    ): PhotoDto

    @GET("/search/photos")
    suspend fun getSearchedPhotos(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("order_by") orderBy: String,
        @Query("content_filter") safeSearch: String,
        @Query("color") color: String? = null,
        @Query("orientation") orientation: String ?= null,
        @Query("client_id") clientId: String
    ): SearchDto
}