package com.example.leafywalls.data.remote.dto

import com.example.leafywalls.domain.model.SearchResult

data class SearchDto(
    val total: Int,
    val total_pages: Int,
    val results: List<PhotoDto>
)

//fun SearchDto.toSearchResult(): SearchResult {
//    return SearchResult(
//        result = results
//    )
//}