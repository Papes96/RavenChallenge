package com.raven.home.presentation.viewmodel

import com.raven.core.bases.StateViewModel
import com.raven.core.bases.ViewState
import com.raven.core.extensions.singleFlowOf
import com.raven.home.domain.usecases.GetNewsParams
import com.raven.home.domain.usecases.GetNewsUseCase
import com.raven.home.presentation.model.Article
import com.raven.home.presentation.model.Period
import com.raven.home.presentation.model.PopularType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
) : StateViewModel<HomeIntent, HomeData>() {

    override fun transformIntent(intentFlow: Flow<HomeIntent>): Flow<ViewState<HomeData>> =
        intentFlow.transform(::intentHandler)

    private suspend fun intentHandler(
        flowCollector: FlowCollector<ViewState<HomeData>>,
        intent: HomeIntent
    ) = flowCollector.emitAll(
        when (intent) {
            is HomeIntent.Load -> handleLoadNews(intent.type, intent.period)
        }.toViewState { _ -> state.value.data?.copy(isLoading = false) }
    )

    fun load(type: PopularType = PopularType.VIEWED, period: Period = Period.ONE_DAY) = emitIntent(
        HomeIntent.Load(type, period)
    )

    private fun handleLoadNews(type: PopularType, period: Period) = singleFlowOf {
        HomeData(
            articles = getNewsUseCase.execute(
                GetNewsParams(
                    type, period
                )
            ).first(),
            isLoading = false,
            type = type,
            period = period
        )
    }.onStart {
        emit(
            HomeData(
                isLoading = true,
                type = type,
                period = period
            )
        )
    }
}

sealed class HomeIntent {
    data class Load(
        val type: PopularType = PopularType.VIEWED,
        val period: Period = Period.ONE_DAY
    ) : HomeIntent()
}

data class HomeData(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean,
    val type: PopularType,
    val period: Period
)
