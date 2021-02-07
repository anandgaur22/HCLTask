package com.anandgaur.hcltask.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anandgaur.hcltask.data.MoviesRepository
import com.anandgaur.hcltask.di.Injection
import com.anandgaur.hcltask.ui.detail.DetailMoviesViewModel
import com.anandgaur.hcltask.ui.favorites.FavouritesViewModel
import com.anandgaur.hcltask.ui.movies.MoviesViewModel

class ViewModelFactory private constructor(private val mMoviesRepository: MoviesRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                return MoviesViewModel(
                    mMoviesRepository
                ) as T
            }

            modelClass.isAssignableFrom(DetailMoviesViewModel::class.java) -> {
                return DetailMoviesViewModel(
                    mMoviesRepository
                ) as T
            }
            modelClass.isAssignableFrom(FavouritesViewModel::class.java) -> {
                return FavouritesViewModel(
                    mMoviesRepository
                ) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}