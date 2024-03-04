package com.raven.home.data.remote

import com.raven.core.extensions.mapError
import com.raven.core.extensions.singleFlowOf
import com.raven.home.data.remote.service.HomeService
import com.raven.home.data.remote.service.mapToHttpError
import com.raven.home.domain.model.network.ArticleDTO
import com.raven.home.presentation.model.Period
import com.raven.home.presentation.model.PopularType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val homeService: HomeService
) {

    suspend fun getNews(popularType: PopularType, period: Period): List<ArticleDTO> =
        singleFlowOf {
            homeService.getNews(popularType.path, period.path)
        }
            .mapError(Throwable::mapToHttpError)
            .map { it.results }
            .first()

}