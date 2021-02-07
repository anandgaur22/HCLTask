package com.anandgaur.hcltask.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.anandgaur.hcltask.data.local.entity.*
import com.anandgaur.hcltask.data.local.room.MoviesDao
import com.anandgaur.hcltask.utils.SortUtils

class LocalDataSource private constructor(private val mMoviesDao: MoviesDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(moviesDao: MoviesDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(moviesDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    // movies
    fun getAllMoviesSortedAsPaged(sort: String): DataSource.Factory<Int, MoviesEntity> {
        val query = SortUtils.getMoviesSortedQuery(sort)
        return mMoviesDao.getAllMoviesSort(query)
    }


    // movies shows
    fun getMovieseWithGenres(moviesId: Int): LiveData<MoviesWithGenres> =
        mMoviesDao.getMoviesWithGenresById(moviesId)

    // movies director
    fun getMovieseWithDirector(moviesId: Int): LiveData<MoviesWithDirector> =
        mMoviesDao.getMoviesWithDirectorById(moviesId)



    fun insertMovie(movies: List<MoviesEntity>) {
        mMoviesDao.insertMovies(movies)
    }

    fun updateMovie(movie: MoviesEntity) {
        mMoviesDao.updateMovies(movie)
    }

    // favorites
    fun setFavoritesMovie(movie: MoviesEntity, state: Boolean) {
        movie.isFavorite = state
        mMoviesDao.updateMovies(movie)
    }

    fun getFavoriteMoviesAsPaged(): DataSource.Factory<Int, MoviesEntity> =
        mMoviesDao.getFavouriteMovie()


    // genres
    fun insertGenresMovies(genres: List<GenresEntity>) = mMoviesDao.insertGenres(genres)

    // director
    fun insertDirectorMovies(director: List<DirectorEntity>) = mMoviesDao.insertDirector(director)


    // language
    fun setLanguageMovie(movie: MoviesEntity, language: String?) {
        movie.language = language
        mMoviesDao.updateMovies(movie)
    }

}

