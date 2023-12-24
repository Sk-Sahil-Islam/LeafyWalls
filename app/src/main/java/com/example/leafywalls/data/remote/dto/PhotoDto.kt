package com.example.leafywalls.data.remote.dto

import com.example.leafywalls.domain.model.Photo

data class PhotoDto(
    val alt_description: String,
    val blur_hash: String,
    val breadcrumbs: List<Breadcrumb>,
    val color: String,
    val created_at: String,
    val current_user_collections: List<Any>,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val promoted_at: Any,
    val slug: String,
    val sponsorship: Sponsorship?,
    val topic_submissions: TopicSubmissions,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
)

fun PhotoDto.toPhoto(): Photo {
    return Photo(
        photoId = id,
        url = urls.raw + "&fm=jpg&q=80",
        isSponsored = sponsorship != null
    )
}