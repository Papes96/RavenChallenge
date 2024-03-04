package com.raven.home

import com.raven.home.data.HomeRepository
import com.raven.home.data.local.HomeLocalDataSource
import com.raven.home.data.remote.HomeRemoteDataSource
import com.raven.home.domain.mapper.HomeMapper
import com.raven.home.domain.model.network.ArticleDTO
import com.raven.home.domain.model.storage.ArticleEntity
import com.raven.home.presentation.model.Period
import com.raven.home.presentation.model.PopularType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeRepositoryTest {

    private var remoteDataSource: HomeRemoteDataSource = mock()
    private var localDataSource: HomeLocalDataSource = mock()
    private var mapper: HomeMapper = mock()

    private lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        homeRepository = HomeRepository(remoteDataSource, localDataSource, mapper)
    }

    @Test
    fun `getNews from remote should not get news from database`() {
        runBlocking {
            val popularType = PopularType.VIEWED
            val period = Period.ONE_DAY
            val articleDtoList = listOf(
                ArticleDTO(
                    url = "",
                    id = 1L,
                    publishedDate = "",
                    byline = "",
                    title = "title",
                    abstract = "abstract",
                    media = listOf()
                )
            )

            `when`(remoteDataSource.getNews(popularType, period)).thenReturn(articleDtoList)

            val result = homeRepository.getNews(popularType, period).toList()

            assertEquals(articleDtoList, result.first())

            verify(localDataSource, never()).getNews(popularType, period)

        }
    }

    @Test
    fun `getNews should return news from remote data source`() {
        runBlocking {
            val popularType = PopularType.VIEWED
            val period = Period.ONE_DAY
            val articleDtoList = listOf(
                ArticleDTO(
                    url = "",
                    id = 1L,
                    publishedDate = "",
                    byline = "",
                    title = "title",
                    abstract = "abstract",
                    media = listOf()
                )
            )

            `when`(remoteDataSource.getNews(popularType, period)).thenReturn(articleDtoList)

            val result = homeRepository.getNews(popularType, period).toList()

            assertEquals(articleDtoList, result.first())
        }
    }

    @Test
    fun `getNews should save news to local data source and recover from local data source on network failure`() {
        runBlocking {
            val popularType = PopularType.VIEWED
            val period = Period.ONE_DAY
            val articleDtoList = listOf(
                ArticleDTO(
                    url = "",
                    id = 1L,
                    publishedDate = "",
                    byline = "",
                    title = "title",
                    abstract = "abstract",
                    media = listOf()
                )
            )
            val articleEntityList = listOf(
                ArticleEntity(
                    articleId = 1L,
                    title = "title",
                    content = "subtitle",
                    media = "media",
                    byline = "byline",
                    url = "url",
                    date = "date"
                )
            )
            `when`(remoteDataSource.getNews(popularType, period)).thenReturn(articleDtoList)
            `when`(mapper.toArticleEntityList(articleDtoList)).thenReturn(articleEntityList)

            val result = homeRepository.getNews(popularType, period).toList()

            assertEquals(articleDtoList, result.first())

            // Verify news is saved in database
            verify(localDataSource).saveNews(articleEntityList, popularType, period)
        }
    }
}
