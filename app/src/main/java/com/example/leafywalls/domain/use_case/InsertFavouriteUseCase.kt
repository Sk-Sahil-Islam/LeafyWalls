package com.example.leafywalls.domain.use_case

import com.example.leafywalls.data.db.Favorite
import com.example.leafywalls.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertFavouriteUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(favourite: Favorite) {
        scope.launch {
            repository.insertFavourite(favorite = favourite)
        }
    }
}