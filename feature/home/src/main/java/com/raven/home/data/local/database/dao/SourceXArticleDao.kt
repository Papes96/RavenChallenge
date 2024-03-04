package com.raven.home.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.raven.home.domain.model.storage.SourceXArticle

@Dao
interface SourceXArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSourceXArticle(sourceXArticle: SourceXArticle)

}