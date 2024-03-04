package com.raven.core.bases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raven.core.bases.ViewState.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class StateViewModel<Intent, Data>(
    private val initialState: ViewState<Data> = ViewState.Initial(),
) : ViewModel() {

    private val intentFlow = MutableSharedFlow<Intent>()

    val state: StateFlow<ViewState<Data>> by lazy {
        transformIntent(intentFlow)
            .catch { throw IllegalStateException("transformIntents emitted an error") }
            .onCompletion { cause -> check(cause != null) { "transformIntents completed unexpectedly" } }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                initialState
            )
    }

    protected fun emitIntent(intent: Intent) {
        state; viewModelScope.launch(Dispatchers.Main) { intentFlow.emit(intent) }
    }

    protected abstract fun transformIntent(intentFlow: Flow<Intent>): Flow<ViewState<Data>>

    protected inline fun Flow<Data>.toViewState(
        intent: Any? = null,
        crossinline dataOnError: (Throwable) -> Data? = { null }
    ) = map<_, ViewState<Data>>(::Success).catch { error ->
        emit(ViewState.Error(error, dataOnError(error)))
    }
}