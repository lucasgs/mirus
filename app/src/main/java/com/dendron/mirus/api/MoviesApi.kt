package com.dendron.mirus.api

import com.dendron.mirus.api.response.DiscoverResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie")
    fun getPopularMoviesAsync(@Query("page") page: Int): Deferred<Response<DiscoverResponse>>

    @GET("search/movie")
    fun searchMoviesAsync(@Query("query") query: String): Deferred<Response<DiscoverResponse>>
}