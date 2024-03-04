package com.raven.home.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.raven.home.domain.model.storage.SourceEntity

@Dao
interface SourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(source: SourceEntity): Long

}