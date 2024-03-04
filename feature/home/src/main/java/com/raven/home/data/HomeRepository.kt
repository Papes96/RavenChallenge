package com.raven.home.data

import com.raven.home.domain.HomeDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor() : HomeDataSource {

    override suspend fun getNews(): Flow<List<Unit>> {
        TODO("Implement your respective remote data source")
    }
}
