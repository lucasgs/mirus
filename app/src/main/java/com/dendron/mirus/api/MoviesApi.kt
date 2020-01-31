package com.dendron.mirus.api

import com.dendron.mirus.api.response.DiscoverResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface MoviesApi {

    @GET("discover/movie")
    fun getPopularMoviesAsync(): Deferred<Response<DiscoverResponse>>
}