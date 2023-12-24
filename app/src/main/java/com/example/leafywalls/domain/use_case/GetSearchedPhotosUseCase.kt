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

class GetSearchedPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    fun invoke(
        query: String,
        orderBy: String,
        safeSearch: String,
//        color: String,
//        orientation: String
    ): Flow<PagingData<PhotoDto>> = Pager(
        PagingConfig(5)
    ) {
        SearchedPhotoListPagingSource(
            repository,
            query,
            orderBy,
            safeSearch,
//            color,
//            orientation
        )
    }.flow.cachedIn(scope)
}

class SearchedPhotoListPagingSource(
    private val repository: PhotoRepository,
    private val query: String,
    private val orderBy: String,
    private val safeSearch: String,
//    private val color: String,
//    private val orientation: String
) : PagingSource<Int, PhotoDto>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoDto>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDto> {
        val page = params.key ?: 1
        return try {
            val response = repository.getSearchedPhotos(
                page = page,
                query = query,
                orderBy = orderBy,
                safeSearch = safeSearch,
//                color = color,
//                orientation = orientation
            )
            val endOfPaginationReached = response.results.isEmpty()
            if (response.results.isNotEmpty()) {
                LoadResult.Page(
                    data = response.results,
                    prevKey = null,
                    nextKey = if (endOfPaginationReached) null else page + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(), prevKey = null, nextKey = null
                )
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}