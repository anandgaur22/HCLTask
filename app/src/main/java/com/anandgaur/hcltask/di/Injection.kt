package com.anandgaur.hcltask.di

import android.content.Context
import com.anandgaur.hcltask.data.MoviesRepository
import com.anandgaur.hcltask.data.local.LocalDataSource
import com.anandgaur.hcltask.data.local.room.MoviesDatabase
import com.anandgaur.hcltask.data.remote.RemoteDataSource
import com.anandgaur.hcltask.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MoviesRepository{
        val database = MoviesDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.moviesDao())
        val appExecutors = AppExecutors()

        return MoviesRepository.getInstance(appExecutors, localDataSource, remoteDataSource)
    }
}