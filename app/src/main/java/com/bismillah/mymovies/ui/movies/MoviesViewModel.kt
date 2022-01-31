package com.bismillah.mymovies.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bismillah.mymovies.data.source.MovieAppRepository
import com.bismillah.mymovies.data.source.local.MovieEntity

class MoviesViewModel(private val movieAppRepository: MovieAppRepository) : ViewModel() {

    fun getMovies(): LiveData<List<MovieEntity>> = movieAppRepository.getAllMovies()

}