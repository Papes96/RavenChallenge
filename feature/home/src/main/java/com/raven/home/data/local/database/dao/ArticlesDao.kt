package com.raven.home.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.raven.home.domain.model.storage.ArticleEntity

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles WHERE article_id = :articleId")
    fun getArticleById(articleId: Long): ArticleEntity?

    @Query(
        "SELECT articles.* FROM articles " +
                "INNER JOIN source_x_article ON articles.article_id = source_x_article.article_id " +
                "WHERE source_x_article.source_id = :sourceId " +
                "ORDER BY source_x_article.source_id"
    )
    fun getArticlesBySourceId(sourceId: String): List<ArticleEntity>

    @Upsert
    fun upsertArticle(article: ArticleEntity): Long

    @Query("DELETE FROM articles WHERE article_id NOT IN (SELECT DISTINCT article_id FROM source_x_article)")
    fun deleteItemsWithoutReferences()

}