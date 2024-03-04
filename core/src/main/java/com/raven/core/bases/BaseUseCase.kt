package com.raven.core.bases

import kotlinx.coroutines.flow.Flow

@Suppress("UNCHECKED_CAST")
abstract class BaseUseCase<Params, Output> {

    abstract suspend fun execute(params: Params): Flow<Output>

    open suspend fun execute(): Flow<Output> = execute(Unit as Params)
}
