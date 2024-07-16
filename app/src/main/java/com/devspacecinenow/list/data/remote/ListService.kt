package com.devspacecinenow.list.data.remote

import com.devspacecinenow.common.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface ListService {

    @GET("now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovies(): Response<MovieResponse>

    @GET("top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

    @GET("upcoming?language=en-US&page=1")
    suspend fun getUpcoming(): Response<MovieResponse>

    @GET("popular?language=en-US&page=1")
    suspend fun getPopular(): Response<MovieResponse>

}