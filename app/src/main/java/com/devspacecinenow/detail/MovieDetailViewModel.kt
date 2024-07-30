package com.devspacecinenow.detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.data.remote.RetrofitClient
import com.devspacecinenow.common.data.remote.model.MovieDto
import com.devspacecinenow.detail.data.DetailService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val detailService: DetailService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiMovieDetail = MutableStateFlow<MovieDto?>(null)
    val uiMovieDetail: StateFlow<MovieDto?> = _uiMovieDetail

    fun fetchMovieDetail(movieId: String) = viewModelScope.launch(dispatcher) {
        val response = detailService.getMovieById(movieId)
        if (response.isSuccessful) {
            _uiMovieDetail.value = response.body()
        } else {
            Log.d("MovieDetailViewModel", "Request Error :: ${response.errorBody()}")
        }
    }

    fun cleanMovieId() {
        viewModelScope.launch {
            delay(1000)
            _uiMovieDetail.value = null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return MovieDetailViewModel(
                    detailService
                ) as T
            }
        }
    }
}