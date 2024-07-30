package com.devspacecinenow.list.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.CineNowApplication
import com.devspacecinenow.list.data.ListRepository
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieListViewModel(
    val repository: ListRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow(MovieListUiState())
    val uiNowPlaying: StateFlow<MovieListUiState> = _uiNowPlaying

    private val _uiTopRated = MutableStateFlow(MovieListUiState())
    val uiTopRated: StateFlow<MovieListUiState> = _uiTopRated

    private val _uiUpcoming = MutableStateFlow(MovieListUiState())
    val uiUpcoming: StateFlow<MovieListUiState> = _uiUpcoming

    private val _uiPopular = MutableStateFlow(MovieListUiState())
    val uiPopular: StateFlow<MovieListUiState> = _uiPopular

    private val _isNetworkAvailable = MutableStateFlow(false)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable

    init {
        fetchNowPlayingMovies()
        fetchTopRatedMovies()
        fetchUpcomingMovies()
        fetchPopularMovies()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun fetchNowPlayingMovies() = viewModelScope.launch(dispatcher) {
        _uiNowPlaying.value = MovieListUiState(isLoading = true)
        val result = repository.getNowPlaying()
        if (result.isSuccess) {
            val movies = result.getOrNull()
            if (movies != null) {
                val movieUiDataList = movies.map { movieDto ->
                    MovieUiData(
                        id = movieDto.id,
                        title = movieDto.title,
                        overview = movieDto.overview,
                        image = movieDto.image
                    )
                }
                _uiNowPlaying.value = MovieListUiState(list = movieUiDataList)
            }
        } else {
            val ex = result.exceptionOrNull()
            if (ex is UnknownHostException) {
                _uiNowPlaying.value = MovieListUiState(
                    isError = true,
                    errorMessage = "Not internet connection"
                )
            } else {
                _uiNowPlaying.value = MovieListUiState(isError = true)
            }
        }
    }

    private fun fetchTopRatedMovies() = viewModelScope.launch(dispatcher) {
        _uiTopRated.value = MovieListUiState(isLoading = true)

        val result = repository.getTopRated()
        if (result.isSuccess) {
            val movies = result.getOrNull()
            if (movies != null) {
                val movieUiDataList = movies.map { movieDto ->
                    MovieUiData(
                        id = movieDto.id,
                        title = movieDto.title,
                        overview = movieDto.overview,
                        image = movieDto.image
                    )
                }
                _uiTopRated.value = MovieListUiState(list = movieUiDataList)
            }
        } else {
            val ex = result.exceptionOrNull()
            if (ex is UnknownHostException) {
                _uiTopRated.value = MovieListUiState(
                    isError = true,
                    errorMessage = "Not internet connection"
                )
            } else {
                _uiTopRated.value = MovieListUiState(isError = true)
            }
        }
    }

    private fun fetchUpcomingMovies() = viewModelScope.launch(dispatcher) {
        _uiUpcoming.value = MovieListUiState(isLoading = true)

        val result = repository.getUpComing()
        if (result.isSuccess) {
            val movies = result.getOrNull()
            if (movies != null) {
                val movieUiDataList = movies.map { movieDto ->
                    MovieUiData(
                        id = movieDto.id,
                        title = movieDto.title,
                        overview = movieDto.overview,
                        image = movieDto.image
                    )
                }
                _uiUpcoming.value = MovieListUiState(list = movieUiDataList)
            }
        } else {
            val ex = result.exceptionOrNull()
            if (ex is UnknownHostException) {
                _uiUpcoming.value = MovieListUiState(
                    isError = true,
                    errorMessage = "Not internet connection"
                )
            } else {
                _uiUpcoming.value = MovieListUiState(isError = true)
            }
        }
    }

    private fun fetchPopularMovies() = viewModelScope.launch(dispatcher) {
        _uiPopular.value = MovieListUiState(isLoading = true)

        val result = repository.getPopular()
        if (result.isSuccess) {
            val movies = result.getOrNull()
            if (movies != null) {
                val movieUiDataList = movies.map { movieDto ->
                    MovieUiData(
                        id = movieDto.id,
                        title = movieDto.title,
                        overview = movieDto.overview,
                        image = movieDto.image
                    )
                }
                _uiPopular.value = MovieListUiState(list = movieUiDataList)
            }
        } else {
            val ex = result.exceptionOrNull()
            if (ex is UnknownHostException) {
                _uiPopular.value = MovieListUiState(
                    isError = true,
                    errorMessage = "Not internet connection"
                )
            } else {
                _uiPopular.value = MovieListUiState(isError = true)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MovieListViewModel(
                    repository = (application as CineNowApplication).repository
                ) as T
            }
        }
    }
}