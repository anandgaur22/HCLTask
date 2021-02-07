package com.anandgaur.hcltask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.anandgaur.hcltask.data.MoviesRepository
import com.anandgaur.hcltask.data.local.entity.MoviesEntity
import com.anandgaur.hcltask.ui.movies.MoviesViewModel
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
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

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
        viewModel = MoviesViewModel(moviesRepository)
    }

    @Test
    fun getAllMoviesSortBy() {
        val dummyMovies = Resource.success(pagedList)
        val movies = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        movies.value = dummyMovies

        Mockito.`when`(moviesRepository.getAllMoviesPage(SortUtils.POPULAR)).thenReturn(movies)
        val moviesEntities = viewModel.getAllMoviesSortBy(SortUtils.POPULAR).value?.data
        Mockito.verify(moviesRepository).getAllMoviesPage(SortUtils.POPULAR)
        assertNotNull(moviesEntities)

        viewModel.getAllMoviesSortBy(SortUtils.POPULAR).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }

}