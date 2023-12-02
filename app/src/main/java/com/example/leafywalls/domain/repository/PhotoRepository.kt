package com.example.leafywalls.domain.repository

import com.example.leafywalls.data.remote.dto.PhotoDetailDto
import com.example.leafywalls.data.remote.dto.PhotoDto

interface PhotoRepository {

    suspend fun getPhotos(page: Int): List<PhotoDto>

    suspend fun getPhotoDetail(photoId: String): PhotoDetailDto
}