package com.example.leafywalls.data.remote

import android.content.res.Resources
import com.example.leafywalls.R
import com.example.leafywalls.data.remote.dto.PhotoDetailDto
import com.example.leafywalls.data.remote.dto.PhotoDto
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
}