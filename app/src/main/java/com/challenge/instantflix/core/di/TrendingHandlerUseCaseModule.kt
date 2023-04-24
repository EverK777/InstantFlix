package com.challenge.instantflix.core.di

import com.challenge.instantflix.core.data.external.repository.RemoteRepository
import com.challenge.instantflix.core.data.internal.repository.LocalDataRepository
import com.challenge.instantflix.core.usecase.TrendingHandlerUseCase
import com.challenge.instantflix.core.usecase.TrendingHandlerUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrendingHandlerUseCaseModule {

    @ViewModelScoped
    @Provides
    fun providesTrendingHandlerUseCase(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): TrendingHandlerUseCase {
        return TrendingHandlerUseCaseImpl(
            localDataRepository = localDataRepository,
            remoteRepository = remoteRepository,
        )
    }
}
