package com.raven.home.presentation.viewmodel

import com.raven.core.bases.StateViewModel
import com.raven.core.bases.ViewState
import com.raven.core.extensions.singleFlowOf
import com.raven.home.domain.usecases.GetArticleUseCase
import com.raven.home.presentation.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase,
) : StateViewModel<ArticleIntent, ArticleData>() {

    override fun transformIntent(intentFlow: Flow<ArticleIntent>): Flow<ViewState<ArticleData>> =
        intentFlow.transform(::intentHandler)

    private suspend fun intentHandler(
        flowCollector: FlowCollector<ViewState<ArticleData>>,
        intent: ArticleIntent
    ) = flowCollector.emitAll(
        when (intent) {
            is ArticleIntent.Load -> handleLoadArticle(intent.articleId)
        }.toViewState { _ -> state.value.data?.copy(isLoading = false) }
    )

    fun load(articleId: Long) = emitIntent(ArticleIntent.Load(articleId))

    private fun handleLoadArticle(articleId: Long) = singleFlowOf {
        ArticleData(
            article = getArticleUseCase.execute(articleId).first(),
            isLoading = false
        )
    }.onStart {
        emit(
            ArticleData(
                isLoading = true
            )
        )
    }
}

sealed class ArticleIntent {
    data class Load(val articleId: Long) : ArticleIntent()
}

data class ArticleData(
    val article: Article? = null,
    val isLoading: Boolean
)
