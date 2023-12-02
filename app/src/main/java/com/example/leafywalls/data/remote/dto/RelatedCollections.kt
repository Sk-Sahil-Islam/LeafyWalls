package com.example.leafywalls.data.remote.dto

data class RelatedCollections(
    val results: List<Result>,
    val total: Int,
    val type: String
)