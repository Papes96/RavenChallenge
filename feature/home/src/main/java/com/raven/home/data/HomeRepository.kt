package com.raven.home.data

import com.raven.core.extensions.recover
import com.raven.core.extensions.singleFlowOf
import com.raven.home.data.local.HomeLocalDataSource
import com.raven.home.domain.model.network.ArticleDTO
import com.raven.home.data.remote.HomeRemoteDataSource
import com.raven.home.domain.HomeDataSource
import com.raven.home.domain.mapper.HomeMapper
import com.raven.home.presentation.model.Period
import com.raven.home.presentation.model.PopularType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val homeLocalDataSource: HomeLocalDataSource,
    private val mapper: HomeMapper
) : HomeDataSource {

    override suspend fun getNews(popularType: PopularType, period: Period): Flow<List<ArticleDTO>> =
        singleFlowOf {
            homeRemoteDataSource.getNews(popularType, period)
        }
            .onEach { remoteNews ->
                val news = mapper.toArticleEntityList(remoteNews)
                homeLocalDataSource.saveNews(news, popularType, period)
            }
            .recover {
                val localNews = homeLocalDataSource.getNews(popularType, period)
                mapper.toArticleDTOList(localNews)
            }
}