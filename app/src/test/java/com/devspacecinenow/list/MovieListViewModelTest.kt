package com.devspacecinenow.list

import app.cash.turbine.test
import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.presentation.MovieListViewModel
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException

class MovieListViewModelTest {

    private val local = FakeMovieListLocalDataSource()
    private val remote = FakeMovieListRemoteDataSource()
    private val repository = FakeMovieListRepository(local, remote)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieListViewModel(repository, testDispatcher)
    }

    @Test
    fun `Given fresh viewModel When collecting to nowPlaying Then assert expected value`() {
        runTest {
            // Given
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.NowPlaying.name
                )
            )
            repository.nowPlayingResult = Result.success(movies)

            // When
            underTest.uiNowPlaying.test {
                // Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "Title 1",
                            overview = "Overview 1",
                            image = "Image 1"
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel When nowPlaying result is failure Then Exception is UnknownHostException`() = runTest {
        // Given
        repository.nowPlayingResult = Result.failure(UnknownHostException())

        // When
        underTest.uiNowPlaying.test {
            // Then
            val expected = MovieListUiState(
                isError = true,
                errorMessage = "Not internet connection"
            )
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `Given ViewModel When nowPlaying fails with non-UnknownHostException Then UI shows error`() = runTest {
        // Given
        repository.nowPlayingResult = Result.failure(Exception())

        // When
        underTest.uiNowPlaying.test {
            // Then
            val expected = MovieListUiState(isError = true)
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to topRated Then assert expected value`() {
        runTest {
            // Given
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.TopRated.name
                )
            )
            repository.topRatedResult = Result.success(movies)

            // When
            underTest.uiTopRated.test {
                // Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "Title 1",
                            overview = "Overview 1",
                            image = "Image 1"
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel When topRated result is failure Then Exception is UnknownHostException`() = runTest {
        // Given
        repository.topRatedResult = Result.failure(UnknownHostException())

        // When
        underTest.uiTopRated.test {
            // Then
            val expected = MovieListUiState(
                isError = true,
                errorMessage = "Not internet connection"
            )
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `Given ViewModel When topRated fails with non-UnknownHostException Then UI shows error`() = runTest {
        // Given
        repository.topRatedResult = Result.failure(Exception())

        // When
        underTest.uiTopRated.test {
            // Then
            val expected = MovieListUiState(isError = true)
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to Upcoming Then assert expected value`() {
        runTest {
            // Given
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.Upcoming.name
                )
            )
            repository.upcomingResult = Result.success(movies)

            // When
            underTest.uiUpcoming.test {
                // Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "Title 1",
                            overview = "Overview 1",
                            image = "Image 1"
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel When upcoming result is failure Then Exception is UnknownHostException`() = runTest {
        // Given
        repository.upcomingResult = Result.failure(UnknownHostException())

        // When
        underTest.uiUpcoming.test {
            // Then
            val expected = MovieListUiState(
                isError = true,
                errorMessage = "Not internet connection"
            )
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `Given ViewModel When upcoming fails with non-UnknownHostException Then UI shows error`() = runTest {
        // Given
        repository.upcomingResult = Result.failure(Exception())

        // When
        underTest.uiUpcoming.test {
            // Then
            val expected = MovieListUiState(isError = true)
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to Popular Then assert expected value`() {
        runTest {
            // Given
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.Popular.name
                )
            )
            repository.popularResult = Result.success(movies)

            // When
            underTest.uiPopular.test {
                // Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "Title 1",
                            overview = "Overview 1",
                            image = "Image 1"
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel When popular result is failure Then Exception is UnknownHostException`() = runTest {
        // Given
        repository.popularResult = Result.failure(UnknownHostException())

        // When
        underTest.uiPopular.test {
            // Then
            val expected = MovieListUiState(
                isError = true,
                errorMessage = "Not internet connection"
            )
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `Given ViewModel When popular fails with non-UnknownHostException Then UI shows error`() = runTest {
        // Given
        repository.popularResult = Result.failure(Exception())

        // When
        underTest.uiPopular.test {
            // Then
            val expected = MovieListUiState(isError = true)
            assertEquals(expected, awaitItem())
        }
    }
}