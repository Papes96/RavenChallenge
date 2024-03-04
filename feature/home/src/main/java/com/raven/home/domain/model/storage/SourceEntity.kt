package com.raven.home.domain.model.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "sources"
)
data class SourceEntity(
    @PrimaryKey
    @ColumnInfo(name = "source_id") val sourceId: String,
)