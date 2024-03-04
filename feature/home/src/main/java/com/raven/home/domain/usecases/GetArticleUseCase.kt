package com.raven.home.domain.usecases

import com.raven.core.bases.BaseUseCase
import com.raven.core.extensions.singleFlowOf
import com.raven.home.data.local.HomeLocalDataSource
import com.raven.home.domain.mapper.HomeMapper
import com.raven.home.presentation.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val dataSource: HomeLocalDataSource,
    private val mapper: HomeMapper
) : BaseUseCase<Long, Article?>() {

    override suspend fun execute(params: Long): Flow<Article?> = singleFlowOf {
        dataSource.getArticle(params)?.let {
            mapper.toArticle(it)
        }
    }
}