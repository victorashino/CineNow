package com.devspacecinenow.list

import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.local.LocalDataSource

class FakeMovieListLocalDataSource : LocalDataSource {

    var nowPlaying = listOf<Movie>()
    var topRated = listOf<Movie>()
    var upcoming = listOf<Movie>()
    var popular = listOf<Movie>()
    var updateItems = listOf<Movie>()

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return nowPlaying
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return topRated
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        return upcoming
    }

    override suspend fun getPopularMovies(): List<Movie> {
        return popular
    }

    override suspend fun updateLocalItems(movies: List<Movie>) {
        updateItems = movies
    }
}