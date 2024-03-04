package com.raven.home.domain.model.network

import com.google.gson.annotations.SerializedName

data class ArticleResponseDTO(
    @SerializedName("status") val status: String,
    @SerializedName("results") val results: List<ArticleDTO>
)

data class ArticleDTO(
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: Long,
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("byline") val byline: String,
    @SerializedName("title") val title: String,
    @SerializedName("abstract") val abstract: String,
    @SerializedName("media") val media: List<MediaDTO>,
)

data class MediaDTO(
    @SerializedName("type") val type: String,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetadataDTO>
)

data class MediaMetadataDTO(
    @SerializedName("url") val url: String
)