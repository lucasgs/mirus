package com.dendron.mirus.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface MoviesApi {

    @GET("discover/movie")
    fun getPopularMoviesAsync(): Deferred<Response<DiscoverResponse>>
}