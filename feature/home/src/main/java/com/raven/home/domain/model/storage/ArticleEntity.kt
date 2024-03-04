package com.raven.home.domain.model.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey @ColumnInfo(name = "article_id") val articleId: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "byline") val byline: String,
    @ColumnInfo(name = "media") val media: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "date") val date: String,
)