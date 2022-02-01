package com.bismillah.mymovies.di

import android.content.Context
import com.bismillah.mymovies.data.source.MovieAppRepository
import com.bismillah.mymovies.data.source.local.LocalDataSource
import com.bismillah.mymovies.data.source.local.room.MovieDatabase
import com.bismillah.mymovies.data.source.remote.RemoteDataSource
import com.bismillah.mymovies.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieAppRepository {

        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieAppRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}