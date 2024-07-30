package com.devspacecinenow.list.data

import com.devspacecinenow.common.data.model.Movie

interface ListRepository {

    suspend fun getNowPlaying(): Result<List<Movie>?>

    suspend fun getTopRated(): Result<List<Movie>?>

    suspend fun getUpComing(): Result<List<Movie>?>

    suspend fun getPopular(): Result<List<Movie>?>
}