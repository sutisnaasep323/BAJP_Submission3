package com.bismillah.mymovies.data.source

import androidx.lifecycle.LiveData
import com.bismillah.mymovies.data.source.local.MovieEntity

interface MovieAppDataSource {

    fun getAllTvShows(): LiveData<List<MovieEntity>>

    fun getTvShowById(tvShowId: String): LiveData<MovieEntity>

    fun getAllMovies(): LiveData<List<MovieEntity>>

    fun getMovieById(movieId: String): LiveData<MovieEntity>

}