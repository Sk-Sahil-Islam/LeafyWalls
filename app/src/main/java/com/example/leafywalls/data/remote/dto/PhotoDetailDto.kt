package com.example.leafywalls.data.remote.dto

import com.example.leafywalls.domain.model.PhotoDetail

data class PhotoDetailDto(
    val alt_description: String?,
    val blur_hash: String?,
    val breadcrumbs: List<Any>?,
    val color: String,
    val created_at: String,
    val current_user_collections: List<Any>?,
    val description: String?,
    val downloads: Int,
    val exif: Exif?,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val location: Location?,
    val meta: Meta?,
    val promoted_at: String?,
    val public_domain: Boolean,
    val related_collections: RelatedCollections?,
    val slug: String,
    val sponsorship: Sponsorship?,
    val tags: List<Tag>?,
    val tags_preview: List<TagsPreview>?,
    val topic_submissions: TopicSubmissions?,
    val topics: List<Any>?,
    val updated_at: String?,
    val urls: Urls,
    val user: User?,
    val views: Long,
    val width: Int
)

fun PhotoDetailDto.toPhotoDetail(): PhotoDetail {
    return PhotoDetail(
        id = id,
        title = alt_description ?: "Unknown",
        createdAt = created_at,
        downloads = downloads,
        likes = likes,
        location = location?.name ?: "Unknown",
        relatedCollections = related_collections,
        links = links,
        url = urls.raw,
        user = user,
        views = views,
        color = color,
        tags = tags?.take(12)?.map { it.title } ?: emptyList()
    )
}