package com.bismillah.mymovies.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.bismillah.mymovies.data.source.MovieAppRepository
import com.bismillah.mymovies.data.source.local.entity.MovieEntity
import com.bismillah.mymovies.utils.SortUtils
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {
    private lateinit var viewModel: FavoriteViewModel
    private val sort = SortUtils.RANDOM

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieAppRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(movieAppRepository)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyMovies = pagedList
        Mockito.`when`(dummyMovies.size).thenReturn(3)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        Mockito.`when`(movieAppRepository.getFavoriteMovies(sort)).thenReturn(movies)
        val movieEntities = viewModel.getFavoriteMovies(sort).value
        Mockito.verify(movieAppRepository).getFavoriteMovies(sort)
        assertNotNull(movieEntities)
        assertEquals(3, movieEntities?.size)

        viewModel.getFavoriteMovies(sort).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getFavoriteTvShows() {
        val dummyTvShows = pagedList
        Mockito.`when`(dummyTvShows.size).thenReturn(3)
        val tvShows = MutableLiveData<PagedList<MovieEntity>>()
        tvShows.value = dummyTvShows

        Mockito.`when`(movieAppRepository.getFavoriteTvShows(sort)).thenReturn(tvShows)
        val tvShowEntities = viewModel.getFavoriteTvShows(sort).value
        Mockito.verify(movieAppRepository).getFavoriteTvShows(sort)
        assertNotNull(tvShowEntities)
        assertEquals(3, tvShowEntities?.size)

        viewModel.getFavoriteTvShows(sort).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShows)
    }

}