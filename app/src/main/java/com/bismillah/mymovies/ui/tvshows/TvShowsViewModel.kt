package com.bismillah.mymovies.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bismillah.mymovies.data.source.MovieAppRepository
import com.bismillah.mymovies.data.source.local.MovieEntity

class TvShowsViewModel(private val movieAppRepository: MovieAppRepository) : ViewModel() {

    fun getTvShows(): LiveData<List<MovieEntity>> = movieAppRepository.getAllTvShows()

}