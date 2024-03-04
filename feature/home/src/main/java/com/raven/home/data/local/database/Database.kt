package com.raven.home.data.local.database

import androidx.room.RoomDatabase
import com.raven.home.data.local.database.dao.ArticlesDao
import com.raven.home.data.local.database.dao.SourceXArticleDao
import com.raven.home.data.local.database.dao.SourcesDao
import com.raven.home.domain.model.storage.ArticleEntity
import com.raven.home.domain.model.storage.SourceEntity
import com.raven.home.domain.model.storage.SourceXArticle

@androidx.room.Database(
    entities = [
        ArticleEntity::class,
        SourceEntity::class,
        SourceXArticle::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
    abstract fun sourcesDao(): SourcesDao
    abstract fun sourcesXArticleDao(): SourceXArticleDao
}