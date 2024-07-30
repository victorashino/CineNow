package com.devspacecinenow.list

import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.remote.RemoteDataSource

class FakeMovieListRemoteDataSource : RemoteDataSource {

    var nowPlaying = listOf<Movie>()
    var topRated = listOf<Movie>()
    var upcoming = listOf<Movie>()
    var popular = listOf<Movie>()

    var shouldReturnError = false
    var exceptionToThrow: Exception? = null

    override suspend fun getNowPlaying(): Result<List<Movie>?> {
        return if (!shouldReturnError) {
            Result.success(nowPlaying)
        } else {
            Result.failure(exceptionToThrow ?: Exception("Something went wrong"))
        }
    }

    override suspend fun getTopRated(): Result<List<Movie>?> {
        return if (!shouldReturnError) {
            Result.success(topRated)
        } else {
            Result.failure(exceptionToThrow ?: Exception("Something went wrong"))
        }
    }

    override suspend fun getUpComing(): Result<List<Movie>?> {
        return if (!shouldReturnError) {
            Result.success(upcoming)
        } else {
            Result.failure(exceptionToThrow ?: Exception("Something went wrong"))
        }
    }

    override suspend fun getPopular(): Result<List<Movie>?> {
        return if (!shouldReturnError) {
            Result.success(popular)
        } else {
            Result.failure(exceptionToThrow ?: Exception("Something went wrong"))
        }
    }
}
