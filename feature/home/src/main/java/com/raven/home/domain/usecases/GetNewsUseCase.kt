package com.raven.home.domain.usecases

import com.raven.core.bases.BaseUseCase
import com.raven.home.domain.HomeDataSource
import com.raven.home.domain.mapper.HomeMapper
import com.raven.home.presentation.model.Article
import com.raven.home.presentation.model.Period
import com.raven.home.presentation.model.PopularType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val dataSource: HomeDataSource,
    private val mapper: HomeMapper
) : BaseUseCase<GetNewsParams, List<Article>>() {

    override suspend fun execute(params: GetNewsParams): Flow<List<Article>> =
        dataSource.getNews(params.popularType, params.period).map {
            mapper.toArticleList(it)
        }
}

data class GetNewsParams(val popularType: PopularType, val period: Period)
