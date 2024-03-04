package com.raven.home.data.local

import com.raven.home.data.local.database.dao.ArticlesDao
import com.raven.home.data.local.database.dao.SourceXArticleDao
import com.raven.home.data.local.database.dao.SourcesDao
import com.raven.home.domain.model.storage.ArticleEntity
import com.raven.home.domain.model.storage.SourceEntity
import com.raven.home.domain.model.storage.SourceXArticle
import com.raven.home.presentation.model.Period
import com.raven.home.presentation.model.PopularType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeLocalDataSource @Inject constructor(
    private val sourcesDao: SourcesDao,
    private val articlesDao: ArticlesDao,
    private val sourceXArticleDao: SourceXArticleDao
) {

    suspend fun getNews(popularType: PopularType, period: Period): List<ArticleEntity> =
        withContext(Dispatchers.IO) {
            val sourceId = "${popularType.path}${period.path}"
            articlesDao.getArticlesBySourceId(sourceId)
        }

    suspend fun saveNews(articles: List<ArticleEntity>, popularType: PopularType, period: Period) =
        withContext(Dispatchers.IO) {
            val sourceId = "${popularType.path}${period.path}"
            sourcesDao.insert(SourceEntity(sourceId = sourceId))
            articlesDao.deleteItemsWithoutReferences()
            articles.forEach { articleEntity ->
                articlesDao.upsertArticle(articleEntity)
                sourceXArticleDao.insertSourceXArticle(
                    SourceXArticle(
                        articleId = articleEntity.articleId,
                        sourceId = sourceId,
                        epoch = System.currentTimeMillis()
                    )
                )
            }
        }

    suspend fun getArticle(articleId: Long): ArticleEntity? =
        withContext(Dispatchers.IO) {
            articlesDao.getArticleById(articleId)
        }
}