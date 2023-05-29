package com.theblackbit.instantflix.core.usecase

import com.theblackbit.instantflix.core.data.external.repository.RemoteRepository
import com.theblackbit.instantflix.core.data.internal.repository.LocalDataRepository
import com.theblackbit.instantflix.core.data.model.Genre
import com.theblackbit.instantflix.core.data.model.TypeRequest
import com.theblackbit.instantflix.core.utils.ApiResultHandle

class GenresHandlerUseCaseImpl(
    private val localDataRepository: LocalDataRepository,
    private val remoteRepository: RemoteRepository,
) : GenresHandlerUseCase {

    override suspend fun storeGenres() {
        val genresCached = localDataRepository.getGenres()
        if (genresCached.isEmpty()) {
            val moviesGenres = remoteRepository.requestGenres(TypeRequest.MOVIE.type)
            val tvShowsGenres = remoteRepository.requestGenres(TypeRequest.TV_SHOW.type)
            if (moviesGenres is ApiResultHandle.Success && tvShowsGenres is ApiResultHandle.Success) {
                val combinedList = mutableListOf<Genre>()
                combinedList.addAll(moviesGenres.value.genres)
                combinedList.addAll(tvShowsGenres.value.genres)
                combinedList.forEach {
                    localDataRepository.upsertGenre(it)
                }
            }
        }
    }
}
