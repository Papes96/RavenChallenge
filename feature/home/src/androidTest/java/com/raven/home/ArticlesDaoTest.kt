package com.raven.home

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raven.home.data.local.database.Database
import com.raven.home.data.local.database.dao.ArticlesDao
import com.raven.home.domain.model.storage.ArticleEntity
import com.raven.home.domain.model.storage.SourceEntity
import com.raven.home.domain.model.storage.SourceXArticle
import com.raven.home.presentation.model.Period
import com.raven.home.presentation.model.PopularType
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArticlesDaoTest {

    private lateinit var articlesDao: ArticlesDao
    private lateinit var db: Database

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, Database::class.java
        ).build()
        articlesDao = db.articlesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun upsertArticleTest() {
        val article = ArticleEntity(
            articleId = 1L,
            title = "title",
            content = "content",
            media = "media",
            byline = "byline",
            url = "url",
            date = "date"
        )
        val articleModified = ArticleEntity(
            articleId = 1L,
            title = "modified",
            content = "content",
            media = "media",
            byline = "byline",
            url = "url",
            date = "date"
        )

        articlesDao.upsertArticle(article)
        assertEquals("title", articlesDao.getArticleById(1L)?.title)

        articlesDao.upsertArticle(articleModified)
        assertEquals("modified", articlesDao.getArticleById(1L)?.title)
    }

    @Test
    @Throws(Exception::class)
    fun deleteItemsWithoutReferencesTest() {
        db.sourcesDao().insert(SourceEntity("${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}"))
        val article = ArticleEntity(
            articleId = 1L,
            title = "title",
            content = "content",
            media = "media",
            byline = "byline",
            url = "url",
            date = "date"
        )

        articlesDao.upsertArticle(article)
        db.sourcesXArticleDao().insertSourceXArticle(
            SourceXArticle(
                1,
                "${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}",
                123L
            )
        )
        db.sourcesDao().insert(SourceEntity("${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}"))

        assertEquals(article, articlesDao.getArticleById(1))
        articlesDao.deleteItemsWithoutReferences()
        assertEquals(null, articlesDao.getArticleById(1))
    }

    @Test
    @Throws(Exception::class)
    fun deleteItemsWithoutReferencesTest222() {
        db.sourcesDao().insert(SourceEntity("${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}"))
        val article = ArticleEntity(
            articleId = 1L,
            title = "title",
            content = "content",
            media = "media",
            byline = "byline",
            url = "url",
            date = "date"
        )

        articlesDao.upsertArticle(article)
        db.sourcesXArticleDao().insertSourceXArticle(
            SourceXArticle(
                1,
                "${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}",
                123L
            )
        )

        assertEquals(article, articlesDao.getArticleById(1))
        articlesDao.deleteItemsWithoutReferences()
        assertEquals(article, articlesDao.getArticleById(1))
    }

    @Test
    @Throws(Exception::class)
    fun deleteItemsWithoutReferencesShouldRemoveOnlyOrphansTest() {
        db.sourcesDao().insert(SourceEntity("${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}"))
        db.sourcesDao().insert(SourceEntity("${PopularType.VIEWED.path}${Period.ONE_DAY.path}"))

        val article = ArticleEntity(
            articleId = 1L,
            title = "title",
            content = "content",
            media = "media",
            byline = "byline",
            url = "url",
            date = "date"
        )

        articlesDao.upsertArticle(article)
        db.sourcesXArticleDao().insertSourceXArticle(
            SourceXArticle(
                1,
                "${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}",
                123L
            )
        )
        db.sourcesXArticleDao().insertSourceXArticle(
            SourceXArticle(
                1,
                "${PopularType.VIEWED.path}${Period.ONE_DAY.path}",
                123L
            )
        )
        db.sourcesDao().insert(SourceEntity("${PopularType.VIEWED.path}${Period.ONE_DAY.path}"))

        assertEquals(article, articlesDao.getArticleById(1))
        articlesDao.deleteItemsWithoutReferences()
        assertEquals(article, articlesDao.getArticleById(1))
    }

    @Test
    @Throws(Exception::class)
    fun assertOrderTest() {
        db.sourcesDao().insert(SourceEntity("${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}"))
        db.sourcesDao().insert(SourceEntity("${PopularType.VIEWED.path}${Period.ONE_DAY.path}"))

        val article1 = ArticleEntity(
            articleId = 1L,
            title = "title",
            content = "content",
            media = "media",
            byline = "byline",
            url = "url",
            date = "date"
        )

        val article2 = ArticleEntity(
            articleId = 2L,
            title = "title",
            content = "content",
            media = "media",
            byline = "byline",
            url = "url",
            date = "date"
        )

        articlesDao.upsertArticle(article1)
        articlesDao.upsertArticle(article2)


        db.sourcesXArticleDao().insertSourceXArticle(
            SourceXArticle(
                1L,
                "${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}",
                123L
            )
        )
        db.sourcesXArticleDao().insertSourceXArticle(
            SourceXArticle(
                2L,
                "${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}",
                123L
            )
        )

        db.sourcesXArticleDao().insertSourceXArticle(
            SourceXArticle(
                2L,
                "${PopularType.VIEWED.path}${Period.ONE_DAY.path}",
                123L
            )
        )
        db.sourcesXArticleDao().insertSourceXArticle(
            SourceXArticle(
                1L,
                "${PopularType.VIEWED.path}${Period.ONE_DAY.path}",
                123L
            )
        )

        assertEquals(article1,
            articlesDao.getArticlesBySourceId("${PopularType.VIEWED.path}${Period.SEVEN_DAYS.path}")
                .first()
        )
        assertEquals(article2,
            articlesDao.getArticlesBySourceId("${PopularType.VIEWED.path}${Period.ONE_DAY.path}")
                .first()
        )
    }
}