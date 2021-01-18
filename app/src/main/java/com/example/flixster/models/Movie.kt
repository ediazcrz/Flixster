package com.example.flixster.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String
)
