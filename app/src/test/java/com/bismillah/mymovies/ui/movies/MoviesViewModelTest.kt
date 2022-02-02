package com.bismillah.mymovies.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.bismillah.mymovies.data.source.MovieAppRepository
import com.bismillah.mymovies.data.source.local.entity.MovieEntity
import com.bismillah.mymovies.ui.viewmodel.MoviesViewModel
import com.bismillah.mymovies.utils.SortUtils
import com.bismillah.mymovies.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel
    private val sort = SortUtils.RANDOM

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieAppRepository

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(movieAppRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        Mockito.`when`(dummyMovies.data?.size).thenReturn(3)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        Mockito.`when`(movieAppRepository.getAllMovies(sort)).thenReturn(movies)
        val movieEntities = viewModel.getMovies(sort).value?.data
        verify(movieAppRepository).getAllMovies(sort)
        assertNotNull(movieEntities)
        assertEquals(3, movieEntities?.size)

        viewModel.getMovies(sort).observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}