package com.dendron.mirus.ui.details

import android.os.Parcelable
import com.dendron.mirus.model.Movie
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieUIModel(val movie: Movie, val isFavorite: Boolean) : Parcelable