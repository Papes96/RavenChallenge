package com.raven.home.domain

import kotlinx.coroutines.flow.Flow

interface HomeDataSource {

    suspend fun getNews(): Flow<List<Unit>>
}
