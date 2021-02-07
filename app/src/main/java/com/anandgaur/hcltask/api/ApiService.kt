package com.anandgaur.hcltask.api

import com.anandgaur.hcltask.data.remote.response.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getAllMoviesPage(
        @Query("api_key") api: String
    ): Deferred<MoviesResponse>


    @GET("movie/{movie_id}")
    fun getDetailMoviesPage2(
        @Path("movie_id") id: Int,
        @Query("api_key") api: String
    ): Call<MoviesObject>


    @GET("movie/{movie_id}/credits")
    fun getDirectorMovies(
        @Path("movie_id") id: Int,
        @Query("api_key") api: String
    ): Deferred<CreditsResponse>
}