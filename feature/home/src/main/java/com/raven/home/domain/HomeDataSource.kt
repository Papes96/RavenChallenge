package com.raven.home.domain

import com.raven.home.domain.model.network.ArticleDTO
import com.raven.home.presentation.model.Period
import com.raven.home.presentation.model.PopularType
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    suspend fun getNews(popularType: PopularType, period: Period): Flow<List<ArticleDTO>>
}
