package com.anandgaur.hcltask.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.anandgaur.hcltask.data.MoviesRepository
import com.anandgaur.hcltask.data.local.entity.MoviesEntity
import com.anandgaur.hcltask.valueobject.Resource


class MoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    fun getAllMoviesSortBy(sort: String): LiveData<Resource<PagedList<MoviesEntity>>> =  moviesRepository.getAllMoviesPage(sort)
}

