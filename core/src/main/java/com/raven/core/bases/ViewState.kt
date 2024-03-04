package com.raven.core.bases

sealed class ViewState<out D>(open val data: D? = null, open val error: Throwable? = null) {
    class Initial<D> : ViewState<D>()
    data class Success<D>(override val data: D) : ViewState<D>()
    data class Error<D>(
        override val error: Throwable,
        override val data: D? = null
    ) : ViewState<D>()
}