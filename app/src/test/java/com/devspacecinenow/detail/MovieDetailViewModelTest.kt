package com.devspacecinenow.detail

import androidx.lifecycle.ViewModel
import app.cash.turbine.test
import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.common.data.remote.model.MovieDto
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class MovieDetailViewModelTest : ViewModel() {

    private val fakeService = FakeDetailService()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieDetailViewModel(fakeService, testDispatcher)
    }

    @Test
    fun `fetchMovieDetail returns expected movie detail`() {
        runTest {
            // Given
            val movie = MovieDto(
                id = 1,
                title = "Title 1",
                overview = "Overview 1",
                postPath = "postPath 1"
            )
            underTest.fetchMovieDetail(movie.id.toString())

            // When
            underTest.uiMovieDetail.test {
                // Then assert expected value
                val expected = MovieDto(
                    id = movie.id,
                    title = "Fake Movie",
                    overview = "This is a fake movie for testing.",
                    postPath = "PostPath",
                )

                assertEquals(expected, awaitItem())
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetchMovieDetail logs error when request fails`() {
        runTest {
            // Given
            fakeService.shouldReturnError = true

            // When
            underTest.fetchMovieDetail("invalid_id")
            advanceUntilIdle()  // Aguarda todas as corrotinas terminarem

            // Then
            val movieDetail = underTest.uiMovieDetail.value
            assertEquals(null, movieDetail)
        }
    }
}