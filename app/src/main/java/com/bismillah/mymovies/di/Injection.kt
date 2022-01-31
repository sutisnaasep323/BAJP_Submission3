package com.bismillah.mymovies.di

import com.bismillah.mymovies.data.source.MovieAppRepository
import com.bismillah.mymovies.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(): MovieAppRepository {
        val remoteRepository = RemoteDataSource.getInstance()
        return MovieAppRepository.getInstance(remoteRepository)
    }
}