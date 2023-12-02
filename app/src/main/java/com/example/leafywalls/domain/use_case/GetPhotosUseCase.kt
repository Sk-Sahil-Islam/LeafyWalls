package com.example.leafywalls.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.leafywalls.data.remote.dto.PhotoDto
import com.example.leafywalls.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(): Flow<PagingData<PhotoDto>> =
        Pager(
            PagingConfig(10)
        ) {
            PhotoListPagingSource(repository)
        }.flow.cachedIn(scope)
}

class PhotoListPagingSource(
    private val repository: PhotoRepository
): PagingSource<Int, PhotoDto>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoDto>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDto> {
        val page = params.key ?: 1
        return try {
            val response = repository.getPhotos(page=page)
            val endOfPaginationReached = response.isEmpty()
            if(response.isNotEmpty()){
                LoadResult.Page(
                    data = response,
                    prevKey = null,
                    nextKey = if(endOfPaginationReached) null else page+1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}