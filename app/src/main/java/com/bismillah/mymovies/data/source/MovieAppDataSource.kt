package com.bismillah.mymovies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.bismillah.mymovies.data.source.local.entity.MovieEntity
import com.bismillah.mymovies.vo.Resource

interface MovieAppDataSource {

    fun getAllTvShows(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getTvShowById(tvShowId: Int): LiveData<Resource<MovieEntity>>

    fun getAllMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieById(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(sort: String): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTvShows(sort: String): LiveData<PagedList<MovieEntity>>

    fun setCourseFavorite(movie: MovieEntity, state: Boolean)

}