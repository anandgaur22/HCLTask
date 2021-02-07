package com.anandgaur.hcltask.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anandgaur.hcltask.data.MoviesRepository
import com.anandgaur.hcltask.data.local.entity.MoviesEntity
import com.anandgaur.hcltask.data.local.entity.MoviesWithDirector
import com.anandgaur.hcltask.data.local.entity.MoviesWithGenres
import com.anandgaur.hcltask.valueobject.Resource

class DetailMoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val moviesId  = MutableLiveData<Int>()

    fun setSelectedMovies(moviesId: Int) {
        this.moviesId.value = moviesId
    }

    //movies
    var moviesDetail: LiveData<Resource<MoviesWithGenres>> = Transformations.switchMap(moviesId) { mMoviesId ->
        moviesRepository.getDetailMovies(mMoviesId)
    }

    var moviesDirector: LiveData<Resource<MoviesWithDirector>> = Transformations.switchMap(moviesId) { mMoviesId ->
        moviesRepository.getMoviesDirector(mMoviesId)
    }



    //favourites
    fun setFavoriteMovie(dataMovie: MoviesEntity, newStatus:Boolean) =
        moviesRepository.setFavoriteMovie(dataMovie, newStatus)
}