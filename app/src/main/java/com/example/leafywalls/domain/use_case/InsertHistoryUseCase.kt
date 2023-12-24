package com.example.leafywalls.domain.use_case

import android.content.Context
import com.example.leafywalls.data.db.History
import com.example.leafywalls.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertHistoryUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    operator fun invoke(history: History) {
        scope.launch {
            repository.insertHistory(history = history)
        }
    }
}