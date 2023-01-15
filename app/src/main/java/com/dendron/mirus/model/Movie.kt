package com.dendron.mirus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String?,
    val title: String,
    val backDropPath: String,
    val voteAverage: Double
) : Parcelable