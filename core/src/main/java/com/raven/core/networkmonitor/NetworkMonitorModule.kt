package com.raven.core.networkmonitor

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkMonitorModule {
    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext appContext: Context
    ): NetworkMonitor = NetworkMonitorImpl(appContext)
}