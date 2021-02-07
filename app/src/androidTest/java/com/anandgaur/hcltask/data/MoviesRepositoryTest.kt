package com.anandgaur.hcltask.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.anandgaur.hcltask.data.local.LocalDataSource
import com.nhaarman.mockitokotlin2.verify
import com.anandgaur.hcltask.data.local.entity.MoviesEntity
import com.anandgaur.hcltask.data.local.entity.MoviesWithDirector
import com.anandgaur.hcltask.data.local.entity.MoviesWithGenres
import com.anandgaur.hcltask.data.remote.RemoteDataSource
import com.anandgaur.hcltask.utils.*
import com.anandgaur.hcltask.valueobject.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito

class MoviesRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val moviesRepository = FakeMoviesRepository(appExecutors, local, remote)

    private val moviesResponses = MoviesDataDummy.generateRemoteDummyMovies()
    private val moviesId = moviesResponses[0].moviesId
    private val moviesWithGenresResponse = MoviesDataDummy.generateRemoteDummyGenres(moviesId)
    private val moviesWithDirectorResponse = MoviesDataDummy.generateRemoteDummyDirector(moviesId)


    @Test
    fun getAllMoviesPage() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        Mockito.`when`(local.getAllMoviesSortedAsPaged(SortUtils.DEFAULT)).thenReturn(dataSourceFactory)
        moviesRepository.getAllMoviesPage(SortUtils.DEFAULT)

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(MoviesDataDummy.generateDummyMovies()))
        verify(local).getAllMoviesSortedAsPaged(SortUtils.DEFAULT)
        assertNotNull(moviesEntities.data)
    }



    @Test
    fun getDetailMovies() {
        val dummyEntity = MutableLiveData<MoviesWithGenres>()
        dummyEntity.value = MoviesDataDummy.generateDummyMoviesWithGenres(MoviesDataDummy.generateDummyMovies()[0], true)
        Mockito.`when`<LiveData<MoviesWithGenres>>(local.getMovieseWithGenres(moviesId)).thenReturn(dummyEntity)

        val moviesEntitiesContent = LiveDataTestUtil.getValue(moviesRepository.getDetailMovies(moviesId))
        verify(local).getMovieseWithGenres(moviesId)
        assertNotNull(moviesEntitiesContent)
        assertNotNull(moviesEntitiesContent.data)
        assertNotNull(moviesEntitiesContent.data?.mMovies)
        assertEquals(moviesResponses[0].title, moviesEntitiesContent.data?.mMovies?.title)
        assertNotNull(moviesEntitiesContent.data?.mGenres)
        assertEquals(moviesWithGenresResponse[0].name, moviesEntitiesContent.data?.mGenres?.get(0)?.name)
    }


    @Test
    fun getDirectorMovies() {
        val dummyEntity = MutableLiveData<MoviesWithDirector>()
        dummyEntity.value = MoviesDataDummy.generateDummyMoviesWithDirector(MoviesDataDummy.generateDummyMovies()[0], true)
        Mockito.`when`<LiveData<MoviesWithDirector>>(local.getMovieseWithDirector(moviesId)).thenReturn(dummyEntity)

        val moviesEntitiesContent = LiveDataTestUtil.getValue(moviesRepository.getMoviesDirector(moviesId))
        verify(local).getMovieseWithDirector(moviesId)
        assertNotNull(moviesEntitiesContent)
        assertNotNull(moviesEntitiesContent.data)
        assertNotNull(moviesEntitiesContent.data?.mMovies)
        assertEquals(moviesResponses[0].title, moviesEntitiesContent.data?.mMovies?.title)
        assertNotNull(moviesEntitiesContent.data?.mDirector)
        assertEquals(moviesWithDirectorResponse[0].name, moviesEntitiesContent.data?.mDirector?.get(0)?.name)
    }


    @Test
    fun getFavoriteMoviesPage() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        Mockito.`when`(local.getFavoriteMoviesAsPaged()).thenReturn(dataSourceFactory)
        moviesRepository.getFavoriteMoviesPage()

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(MoviesDataDummy.generateDummyMovies()))
        verify(local).getFavoriteMoviesAsPaged()
        assertNotNull(moviesEntities)
        assertEquals(moviesResponses.size.toLong(), moviesEntities.data?.size?.toLong())
    }

}