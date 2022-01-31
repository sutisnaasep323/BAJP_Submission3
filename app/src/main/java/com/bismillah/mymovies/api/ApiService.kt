package com.bismillah.mymovies.api

import com.bismillah.mymovies.BuildConfig
import com.bismillah.mymovies.data.source.remote.response.MoviesResponse
import com.bismillah.mymovies.data.source.remote.response.TvShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<MoviesResponse>

    @GET("tv/popular")
    fun getTvShows(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<TvShowsResponse>
}