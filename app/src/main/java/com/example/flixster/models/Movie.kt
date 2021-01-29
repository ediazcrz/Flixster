package com.example.flixster.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class Movie(
    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("vote_average")
    val rating: Double
) : Parcelable
