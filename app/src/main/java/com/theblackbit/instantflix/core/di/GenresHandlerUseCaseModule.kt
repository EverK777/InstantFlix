package com.theblackbit.instantflix.core.di

import com.theblackbit.instantflix.core.data.external.repository.RemoteRepository
import com.theblackbit.instantflix.core.data.internal.repository.LocalDataRepository
import com.theblackbit.instantflix.core.usecase.GenresHandlerUseCase
import com.theblackbit.instantflix.core.usecase.GenresHandlerUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GenresHandlerUseCaseModule {

    @ViewModelScoped
    @Provides
    fun providesGenresHandlerUseCase(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): GenresHandlerUseCase {
        return GenresHandlerUseCaseImpl(
            localDataRepository = localDataRepository,
            remoteRepository = remoteRepository,
        )
    }
}
