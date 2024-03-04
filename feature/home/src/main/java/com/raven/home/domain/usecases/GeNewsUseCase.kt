package com.raven.home.domain.usecases

import com.raven.core.bases.BaseUseCase
import com.raven.home.domain.HomeDataSource
import com.raven.home.domain.mapper.GetNewsMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GeNewsUseCase @Inject constructor(
    private val dataSource: HomeDataSource,
    private val mapper: GetNewsMapper
) : BaseUseCase<Unit, List<String>>() {

    override fun execute(params: Unit): Flow<List<String>> {
        TODO("Get the news list from your repository and use your mapper for your viewModel")
    }
}
