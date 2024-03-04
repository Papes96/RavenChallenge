package com.raven.core.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Creates a [Flow] which emits a single value based on the result of the [block]
 */
inline fun <T> singleFlowOf(crossinline block: suspend () -> T) =
    flow { emit(block()) }.flowOn(Dispatchers.IO)

/**
 * Map error emitted by this [Flow] into other types [E] of errors via block
 */
inline fun <T, E : Throwable> Flow<T>.mapError(crossinline block: suspend (Throwable) -> E) =
    catch { throw block(it) }

/**
 * Catch errors emitted by this [Flow] and map the to non-error type [T] via block
 */
inline fun <T> Flow<T>.recover(crossinline block: suspend (Throwable) -> T) =
    catch { emit(block(it)) }