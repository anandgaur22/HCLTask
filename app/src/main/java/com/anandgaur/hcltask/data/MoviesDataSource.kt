package com.anandgaur.hcltask.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.anandgaur.hcltask.data.local.entity.MoviesEntity
import com.anandgaur.hcltask.data.local.entity.MoviesWithDirector
import com.anandgaur.hcltask.data.local.entity.MoviesWithGenres
import com.anandgaur.hcltask.valueobject.Resource

interface MoviesDataSource {

    fun getAllMoviesPage(sort: String): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getDetailMovies(moviesId: Int): LiveData<Resource<MoviesWithGenres>>

    fun getMoviesDirector(moviesId: Int): LiveData<Resource<MoviesWithDirector>>

    fun setFavoriteMovie(course: MoviesEntity, state: Boolean)

    fun getFavoriteMoviesPage(): LiveData<Resource<PagedList<MoviesEntity>>>

}