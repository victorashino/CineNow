package com.devspacecinenow.common.data.remote.model

@kotlinx.serialization.Serializable
data class MovieResponse(
    val results: List<MovieDto>
)
