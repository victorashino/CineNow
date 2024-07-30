package com.devspacecinenow.list

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.MovieListRepository
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import java.net.UnknownHostException

class MovieListRepositoryTest {

    private val local = FakeMovieListLocalDataSource()
    private val remote = FakeMovieListRemoteDataSource()

    private val underTest by lazy {
        MovieListRepository(
            local = local,
            remote = remote
        )
    }

    // Playing Now
    @Test
    fun `Given no internet connection When getting now playing movies Then return local data`() {
        runTest {

            // Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.NowPlaying.name
                )
            )

            remote.nowPlaying = emptyList()
            local.nowPlaying = localList

            //  When
            val result = underTest.getNowPlaying()

            // Then
            val expected = Result.success(local.nowPlaying)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting now playing movies Then return remote result`() {
        runTest {
            // Given
            val remoteException = UnknownHostException("No internet")

            remote.shouldReturnError = true
            remote.exceptionToThrow = remoteException

            remote.nowPlaying = emptyList()
            local.nowPlaying = emptyList()

            // When
            val result = underTest.getNowPlaying()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { exception ->
                assertEquals(remoteException::class, exception::class)
                assertEquals(remoteException.message, exception.message)
            }
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting now playing movies Then return local result`() {
        runTest {
            // Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.NowPlaying.name
                )
            )

            val remoteException = UnknownHostException("No internet")

            remote.shouldReturnError = true
            remote.exceptionToThrow = remoteException

            remote.nowPlaying = emptyList()
            local.nowPlaying = localList

            // When
            val result = underTest.getNowPlaying()

            // Then
            val expected = Result.success(localList)
            assert(result.isSuccess)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote success When getting now playing movies Then update local data`() {
        runTest {
            // Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.NowPlaying.name
                )
            )
            val remoteResult = Result.success(list)
            remote.nowPlaying = remoteResult.getOrNull()!!
            local.nowPlaying = list

            // When
            val result = underTest.getNowPlaying()

            // Then
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)
        }
    }

    @Test
    fun `Given remote throws exception When getting now playing movies Then return failure result`() {
        runTest {
            // Given
            val exception = Exception("Something went wrong")
            remote.shouldReturnError = true
            remote.exceptionToThrow = exception

            // When
            val result = underTest.getNowPlaying()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { ex ->
                assertEquals(exception::class, ex::class)
                assertEquals(exception.message, ex.message)
            }
        }
    }

    // Top Rated
    @Test
    fun `Given no internet connection When getting top rated movies Then return local data`() {
        runTest {

            // Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.TopRated.name
                )
            )

            remote.topRated = emptyList()
            local.topRated = localList

            //  When
            val result = underTest.getTopRated()

            // Then
            val expected = Result.success(localList)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting top rated movies Then return remote result`() {
        runTest {
            // Given
            val remoteException = UnknownHostException("No internet")

            remote.exceptionToThrow = remoteException
            remote.shouldReturnError = true

            remote.topRated = emptyList()
            local.topRated = emptyList()

            // When
            val result = underTest.getTopRated()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { exception ->
                assertEquals(remoteException::class, exception::class)
                assertEquals(remoteException.message, exception.message)
            }
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting top rated movies Then return local result`() {
        runTest {
            // Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.TopRated.name
                )
            )

            val remoteException = UnknownHostException("No internet")

            remote.shouldReturnError = true
            remote.exceptionToThrow = remoteException

            remote.topRated = emptyList()
            local.topRated = localList

            // When
            val result = underTest.getTopRated()

            // Then
            val expected = Result.success(localList)
            assert(result.isSuccess)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote success When getting top rated movies Then update local data`() {
        runTest {
            // Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.Upcoming.name
                )
            )
            val remoteResult = Result.success(list)
            remote.topRated = remoteResult.getOrNull() ?: emptyList()
            local.topRated = list

            // When
            val result = underTest.getTopRated()

            // Then
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)
        }
    }

    @Test
    fun `Given remote throws exception When getting top rated movies Then return failure result`() {
        runTest {
            // Given
            val exception = Exception("Something went wrong")
            remote.shouldReturnError = true
            remote.exceptionToThrow = exception


            // When
            val result = underTest.getTopRated()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { ex ->
                assertEquals(exception::class, ex::class)
                assertEquals(exception.message, ex.message)
            }
        }
    }

    // Upcoming
    @Test
    fun `Given no internet connection When getting upcoming movies Then return local data`() {
        runTest {

            // Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.Upcoming.name
                )
            )

            remote.upcoming = emptyList()
            local.upcoming = localList

            //  When
            val result = underTest.getUpComing()

            // Then
            val expected = Result.success(localList)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting upcoming movies Then return remote result`() {
        runTest {
            // Given
            val remoteException = UnknownHostException("No internet")

            remote.exceptionToThrow = remoteException
            remote.shouldReturnError = true

            remote.upcoming = emptyList()
            local.upcoming = emptyList()

            // When
            val result = underTest.getUpComing()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { exception ->
                assertEquals(remoteException::class, exception::class)
                assertEquals(remoteException.message, exception.message)
            }
        }
    }

    @Test
    fun `Given remote success When getting upcoming movies Then update local data`() {
        runTest {
            // Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.Upcoming.name
                )
            )
            val remoteResult = Result.success(list)
            remote.upcoming = remoteResult.getOrNull() ?: emptyList()
            local.upcoming = list

            // When
            val result = underTest.getUpComing()

            // Then
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting upcoming movies Then return local result`() {
        runTest {
            // Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.Upcoming.name
                )
            )

            val remoteException = UnknownHostException("No internet")

            remote.shouldReturnError = true
            remote.exceptionToThrow = remoteException

            remote.upcoming = emptyList()
            local.upcoming = localList

            // When
            val result = underTest.getUpComing()

            // Then
            val expected = Result.success(localList)
            assert(result.isSuccess)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote throws exception When getting upcoming movies Then return failure result`() {
        runTest {
            // Given
            val exception = Exception("Something went wrong")
            remote.shouldReturnError = true
            remote.exceptionToThrow = exception


            // When
            val result = underTest.getUpComing()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { ex ->
                println(ex.message)
                assertEquals(exception::class, ex::class)
                assertEquals(exception.message, ex.message)
            }
        }
    }

    // Popular
    @Test
    fun `Given no internet connection When getting popular movies Then return local data`() {
        runTest {

            // Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.Popular.name
                )
            )

            remote.popular = emptyList()
            local.popular = localList

            //  When
            val result = underTest.getPopular()

            // Then
            val expected = Result.success(localList)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting popular movies Then return remote result`() {
        runTest {
            // Given
            val remoteException = UnknownHostException("No internet")

            remote.exceptionToThrow = remoteException
            remote.shouldReturnError = true

            remote.popular = emptyList()
            local.popular = emptyList()

            // When
            val result = underTest.getPopular()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { exception ->
                assertEquals(remoteException::class, exception::class)
                assertEquals(remoteException.message, exception.message)
            }
        }
    }

    @Test
    fun `Given remote success When getting popular movies Then update local data`() {
        runTest {
            // Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.Popular.name
                )
            )
            val remoteResult = Result.success(list)
            remote.popular = remoteResult.getOrNull() ?: emptyList()
            local.popular = list

            // When
            val result = underTest.getPopular()

            // Then
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting popular movies Then return local result`() {
        runTest {
            // Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "Title 1",
                    overview = "Overview 1",
                    image = "Image 1",
                    category = MovieCategory.Popular.name
                )
            )

            val remoteException = UnknownHostException("No internet")

            remote.shouldReturnError = true
            remote.exceptionToThrow = remoteException

            remote.popular = emptyList()
            local.popular = localList

            // When
            val result = underTest.getPopular()

            // Then
            val expected = Result.success(localList)
            assert(result.isSuccess)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote throws exception When getting popular movies Then return failure result`() {
        runTest {
            // Given
            val exception = Exception("Something went wrong")
            remote.shouldReturnError = true
            remote.exceptionToThrow = exception


            // When
            val result = underTest.getPopular()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { ex ->
                assertEquals(exception::class, ex::class)
                assertEquals(exception.message, ex.message)
            }
        }
    }
}