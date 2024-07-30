package com.devspacecinenow.detail

import com.devspacecinenow.common.data.remote.model.MovieDto
import com.devspacecinenow.detail.data.DetailService
import okhttp3.ResponseBody
import retrofit2.Response


class FakeDetailService : DetailService {

    var shouldReturnError = false

    override suspend fun getMovieById(movieId: String): Response<MovieDto> {
        return if (shouldReturnError) {
            Response.success(null)
        } else {
            val fakeMovie = MovieDto(
                id = movieId.toInt(),
                title = "Fake Movie",
                overview = "This is a fake movie for testing.",
                postPath = "PostPath"
            )
            Response.success(fakeMovie)
        }
    }
}