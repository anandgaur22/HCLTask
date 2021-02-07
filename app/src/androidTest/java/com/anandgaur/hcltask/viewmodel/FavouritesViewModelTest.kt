package com.anandgaur.hcltask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.anandgaur.hcltask.data.MoviesRepository
import com.anandgaur.hcltask.data.local.entity.MoviesEntity
import com.anandgaur.hcltask.ui.favorites.FavouritesViewModel
import com.anandgaur.hcltask.utils.SortUtils
import com.anandgaur.hcltask.valueobject.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavouritesViewModelTest {

    private lateinit var viewModel: FavouritesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MoviesEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MoviesEntity>

    @Before
    fun setUp() {
        viewModel = FavouritesViewModel(moviesRepository)
    }

    @Test
    fun getFavoritesMovies() {
        val dummyMovies = Resource.success(pagedList)
        val movies = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        movies.value = dummyMovies

        Mockito.`when`(moviesRepository.getFavoriteMoviesPage()).thenReturn(movies)
        val moviesEntities = viewModel.getFavoritesMovies().value?.data
        Mockito.verify(moviesRepository).getFavoriteMoviesPage()
        assertNotNull(moviesEntities)

        viewModel.getFavoritesMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getFavoritesTvShows() {
        val dummyMovies = Resource.success(pagedList)
        val tvShows = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        tvShows.value = dummyMovies

    }
}