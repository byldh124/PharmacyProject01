package com.moondroid.pharmacyproject01.di

import com.moondroid.pharmacyproject01.data.repository.RepositoryImpl
import com.moondroid.pharmacyproject01.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideAppRepository(repository: RepositoryImpl): Repository
}