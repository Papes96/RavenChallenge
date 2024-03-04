package com.raven.home.di

import android.content.Context
import androidx.room.Room
import com.raven.home.data.HomeRepository
import com.raven.home.data.local.HomeLocalDataSource
import com.raven.home.data.remote.service.HomeService
import com.raven.home.data.local.database.Database
import com.raven.home.data.local.database.dao.ArticlesDao
import com.raven.home.data.local.database.dao.SourceXArticleDao
import com.raven.home.data.local.database.dao.SourcesDao
import com.raven.home.data.remote.HomeRemoteDataSource
import com.raven.home.domain.HomeDataSource
import com.raven.home.domain.mapper.HomeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return retrofit.newBuilder().build().create(HomeService::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeStorage(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "RavenNews.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideHomeRemoteDataSource(homeService: HomeService): HomeRemoteDataSource =
        HomeRemoteDataSource(homeService)

    @Singleton
    @Provides
    fun provideHomeLocalDataSource(
        sourcesDao: SourcesDao,
        articlesDao: ArticlesDao,
        sourceXArticleDao: SourceXArticleDao
    ): HomeLocalDataSource =
        HomeLocalDataSource(
            sourcesDao = sourcesDao,
            articlesDao = articlesDao,
            sourceXArticleDao = sourceXArticleDao
        )

    @Singleton
    @Provides
    fun provideArticlesDao(database: Database): ArticlesDao =
        database.articlesDao()

    @Singleton
    @Provides
    fun provideSourceXArticleDao(database: Database): SourceXArticleDao =
        database.sourcesXArticleDao()

    @Singleton
    @Provides
    fun provideSourcesDao(database: Database): SourcesDao =
        database.sourcesDao()

    @Provides
    fun provideHomeRepository(
        homeRemoteDataSource: HomeRemoteDataSource,
        homeLocalDataSource: HomeLocalDataSource,
        mapper: HomeMapper
    ): HomeDataSource {
        return HomeRepository(
            homeRemoteDataSource = homeRemoteDataSource,
            homeLocalDataSource = homeLocalDataSource,
            mapper = mapper
        )
    }
}
