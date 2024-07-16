package com.devspacecinenow.common.data.model

import androidx.room.PrimaryKey

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val image: String,
    val category: String
)
