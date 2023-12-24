package com.example.leafywalls.domain.model

import com.example.leafywalls.data.remote.dto.PhotoDto

data class SearchResult(
    val result: List<PhotoDto>
)