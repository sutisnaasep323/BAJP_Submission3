package com.bismillah.mymovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bismillah.mymovies.data.source.MovieAppRepository
import com.bismillah.mymovies.data.source.local.MovieEntity

class DetailViewModel(private val movieAppRepository: MovieAppRepository) : ViewModel() {

    private lateinit var movieId: String
    private lateinit var tvShowId: String

    fun selectedMovieId(movieId: String) {
        this.movieId = movieId
    }

    fun selectedTvShowId(tvShowId: String) {
        this.tvShowId = tvShowId
    }

    fun getMovieDetail(): LiveData<MovieEntity> =
        movieAppRepository.getMovieById(movieId)

    fun getTvShowDetail(): LiveData<MovieEntity> =
        movieAppRepository.getTvShowById(tvShowId)

}