package com.raven.home.domain.model.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "source_x_article",
    primaryKeys = ["article_id", "source_id"],
    foreignKeys = [
        ForeignKey(
            entity = ArticleEntity::class,
            parentColumns = ["article_id"],
            childColumns = ["article_id"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = SourceEntity::class,
            parentColumns = ["source_id"],
            childColumns = ["source_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class SourceXArticle(
    @ColumnInfo(name = "article_id", index = true)
    val articleId: Long,
    @ColumnInfo(name = "source_id", index = true)
    val sourceId: String,
    @ColumnInfo(name = "epoch", index = true)
    val epoch: Long,
)