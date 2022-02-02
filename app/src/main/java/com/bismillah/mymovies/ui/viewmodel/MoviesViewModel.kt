package com.bismillah.mymovies.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bismillah.mymovies.data.source.MovieAppRepository
import com.bismillah.mymovies.data.source.local.entity.MovieEntity
import com.bismillah.mymovies.vo.Resource

class MoviesViewModel(private val movieAppRepository: MovieAppRepository) : ViewModel() {

    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> =
        movieAppRepository.getAllMovies(sort)

}