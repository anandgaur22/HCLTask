package com.anandgaur.hcltask.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.anandgaur.hcltask.data.MoviesRepository
import com.anandgaur.hcltask.data.local.entity.MoviesEntity
import com.anandgaur.hcltask.valueobject.Resource

class FavouritesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    fun setFavouritesMovies(moviesEntity: MoviesEntity) {
        val newState = !moviesEntity.isFavorite
        moviesRepository.setFavoriteMovie(moviesEntity, newState)
    }

    fun getFavoritesMovies(): LiveData<Resource<PagedList<MoviesEntity>>> = moviesRepository.getFavoriteMoviesPage()



}