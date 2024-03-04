package com.raven.home.domain.mapper

import com.raven.home.domain.model.network.ArticleDTO
import com.raven.home.domain.model.network.MediaDTO
import com.raven.home.domain.model.network.MediaMetadataDTO
import com.raven.home.domain.model.storage.ArticleEntity
import com.raven.home.presentation.model.Article
import javax.inject.Inject

class HomeMapper @Inject constructor() {

    fun toArticleList(articles: List<ArticleDTO>): List<Article> {
        return articles.map { it.toArticle() }
    }

    fun toArticleEntityList(articles: List<ArticleDTO>): List<ArticleEntity> {
        return articles.map { it.toArticleEntity() }
    }

    fun toArticleDTOList(articles: List<ArticleEntity>): List<ArticleDTO> {
        return articles.map { it.toArticleDTO() }
    }

    fun toArticle(article: ArticleEntity): Article {
        return with(article) {
            Article(
                id = articleId,
                title = title,
                media = media,
                date = date,
                content = content,
                byline = byline,
                url = url
            )
        }
    }
}

private fun ArticleEntity.toArticleDTO(): ArticleDTO =
    ArticleDTO(
        id = articleId,
        title = title,
        url = url,
        byline = byline,
        publishedDate = date,
        abstract = content,
        media = listOf(
            MediaDTO(
                type = "image",
                mediaMetadata = listOf(
                    MediaMetadataDTO(
                        url = media
                    )
                )
            )
        )
    )

private fun ArticleDTO.toArticleEntity(): ArticleEntity =
    ArticleEntity(
        articleId = id,
        title = title,
        url = url,
        byline = byline,
        date = publishedDate,
        content = abstract,
        media = media.firstOrNull()?.mediaMetadata?.lastOrNull()?.url ?: ""
    )

fun ArticleDTO.toArticle(): Article =
    Article(
        id = id,
        title = title,
        media = media.firstOrNull()?.mediaMetadata?.lastOrNull()?.url ?: "",
        date = publishedDate,
        byline = byline,
        content = abstract,
        url = url
    )
