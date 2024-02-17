package com.example.leafywalls.domain.model

import com.example.leafywalls.data.remote.dto.Links
import com.example.leafywalls.data.remote.dto.RelatedCollections
import com.example.leafywalls.data.remote.dto.Tag
import com.example.leafywalls.data.remote.dto.User

data class PhotoDetail(
    val title: String,
    val color: String,
    val createdAt: String,
    val downloads: Long,
    val id: String,
    val likes: Long,
//    val downloadLink: String,
    val tags: List<String>,
    val links: Links,
    val location: String,
    val relatedCollections: RelatedCollections?,
    val url: String,
    val user: User?,
    val views: Long
)


