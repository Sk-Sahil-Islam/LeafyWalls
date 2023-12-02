package com.example.leafywalls.domain.use_case

import com.example.leafywalls.common.Resource
import com.example.leafywalls.data.remote.dto.toPhoto
import com.example.leafywalls.data.remote.dto.toPhotoDetail
import com.example.leafywalls.domain.model.Photo
import com.example.leafywalls.domain.model.PhotoDetail
import com.example.leafywalls.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(photoId: String): Flow<Resource<PhotoDetail>> = flow {
        try {
            emit(Resource.Loading())
            val photo = repository.getPhotoDetail(photoId).toPhotoDetail()
            emit(Resource.Success(photo))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

}