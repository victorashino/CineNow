package com.devspacecinenow.list

import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.ListRepository
import com.devspacecinenow.list.data.local.LocalDataSource
import com.devspacecinenow.list.data.remote.RemoteDataSource

class FakeMovieListRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ListRepository {
    var nowPlayingResult: Result<List<Movie>?> = Result.success(emptyList())
    var topRatedResult: Result<List<Movie>?> = Result.success(emptyList())
    var upcomingResult: Result<List<Movie>?> = Result.success(emptyList())
    var popularResult: Result<List<Movie>?> = Result.success(emptyList())

    override suspend fun getNowPlaying(): Result<List<Movie>?> = nowPlayingResult
    override suspend fun getTopRated(): Result<List<Movie>?> = topRatedResult
    override suspend fun getUpComing(): Result<List<Movie>?> = upcomingResult
    override suspend fun getPopular(): Result<List<Movie>?> = popularResult
}
