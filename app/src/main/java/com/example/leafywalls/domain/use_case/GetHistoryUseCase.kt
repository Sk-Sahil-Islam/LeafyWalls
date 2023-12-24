package com.example.leafywalls.domain.use_case

import com.example.leafywalls.domain.repository.PhotoRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke() = repository.getHistory()

}
