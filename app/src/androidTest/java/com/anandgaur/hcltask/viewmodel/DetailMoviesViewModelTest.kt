package com.anandgaur.hcltask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anandgaur.hcltask.data.MoviesRepository
import com.anandgaur.hcltask.data.local.entity.MoviesWithDirector
import com.anandgaur.hcltask.data.local.entity.MoviesWithGenres
import com.anandgaur.hcltask.ui.detail.DetailMoviesViewModel
import com.anandgaur.hcltask.utils.MoviesDataDummy
import com.anandgaur.hcltask.valueobject.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMoviesViewModelTest {

    private lateinit var viewModel: DetailMoviesViewModel
    private val dummyMovies = MoviesDataDummy.generateDummyMovies()[0]
    private val dummymoviesId: Int = dummyMovies.moviesId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var observerMoviesGenres: Observer<Resource<MoviesWithGenres>>

    @Mock
    private lateinit var observerMoviesDirector: Observer<Resource<MoviesWithDirector>>

    @Before
    fun setUp() {
        viewModel = DetailMoviesViewModel(moviesRepository)
    }

    // test movies
    @Test
    fun getDetailMovies(){
        val dummyMoviesWithGenres = Resource.success(MoviesDataDummy.generateDummyMoviesWithGenres(dummyMovies, true))
        val movies = MutableLiveData<Resource<MoviesWithGenres>>()
        movies.value = dummyMoviesWithGenres
        viewModel.setSelectedMovies(dummymoviesId)
        Mockito.`when`(moviesRepository.getDetailMovies(dummymoviesId)).thenReturn(movies)

        viewModel.moviesDetail.observeForever(observerMoviesGenres)
        Mockito.verify(observerMoviesGenres).onChanged(dummyMoviesWithGenres)
    }

    @Test
    fun getDirectorMovies(){
        val dummyMoviesWithDirector = Resource.success(MoviesDataDummy.generateDummyMoviesWithDirector(dummyMovies, true))
        val movies = MutableLiveData<Resource<MoviesWithDirector>>()
        movies.value = dummyMoviesWithDirector
        viewModel.setSelectedMovies(dummymoviesId)
        Mockito.`when`(moviesRepository.getMoviesDirector(dummymoviesId)).thenReturn(movies)

        viewModel.moviesDirector.observeForever(observerMoviesDirector)
        Mockito.verify(observerMoviesDirector).onChanged(dummyMoviesWithDirector)
    }


}