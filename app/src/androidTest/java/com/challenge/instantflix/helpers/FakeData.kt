package com.challenge.instantflix.helpers

import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest

val fakeTrendingMovie: MovieTvEntity = MovieTvEntity(
    id = 12345,
    title = "The Super Mario Bros. Movie",
    overview = "While working underground to fix a water main, Brooklyn plumbers—and brothers—Mario and Luigi are transported down a mysterious pipe and wander into a magical new world. But when the brothers are separated, Mario embarks on an epic quest to find Luigi.",
    popularity = 14176.744,
    genres = listOf("Animation", "Comedy", "Drama", "Adventure"),
    voteAverage = 7.5,
    releaseDate = "2023-04-05",
    posterPath = "/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg",
    backdropPath = "/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg",
    genreIdRequested = null,
    requestCategory = RequestCategory.TRENDING,
    typeRequest = TypeRequest.MOVIE,
    page = 1,
    totalResults = 20,
)
