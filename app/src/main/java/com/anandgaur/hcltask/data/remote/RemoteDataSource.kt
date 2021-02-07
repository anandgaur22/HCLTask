package com.anandgaur.hcltask.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anandgaur.hcltask.BuildConfig
import com.anandgaur.hcltask.api.ApiConfig
import com.anandgaur.hcltask.data.remote.response.CreditsResponse
import com.anandgaur.hcltask.data.remote.response.MoviesObject
import com.anandgaur.hcltask.utils.API_KEY
import com.anandgaur.hcltask.utils.EspressoIdlingResource
import kotlinx.coroutines.*
import retrofit2.await

class RemoteDataSource {

    companion object {
        private const val TAG = "RemoteDataSource"
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000
        internal const val APP_ID = API_KEY

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getMoviesAsLiveData(): LiveData<ApiResponse<List<MoviesObject>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MoviesObject>>>()
        CoroutineScope(Dispatchers.IO).launch {
            delay(SERVICE_LATENCY_IN_MILLIS)

            try {
                val postsRequest = ApiConfig.getApiService().getAllMoviesPage(APP_ID)
                val postsResponse = postsRequest.await().results
                resultMovie.postValue(ApiResponse.success(postsResponse as List<MoviesObject>))
                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
            }
        }
        return resultMovie
    }


    fun getDetailMoviesAsLiveData(moviesId: Int): LiveData<ApiResponse<MoviesObject>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MoviesObject>>()
        CoroutineScope(Dispatchers.IO).launch {
            delay(SERVICE_LATENCY_IN_MILLIS)

            try {
                val postsRequest = ApiConfig.getApiService().getDetailMoviesPage2(moviesId, APP_ID)
                val postsResponse = postsRequest.await()
                Log.d(TAG, "cek postResponse movies: ${postsResponse}")
                resultMovie.postValue(ApiResponse.success(postsResponse))

                EspressoIdlingResource.decrement()
            } catch (ex: Exception) {
                Log.e(TAG, "Error response movies: "+ ex.message)
            }
        }
        return resultMovie
    }


    fun getDirectorMoviesAsLiveData(moviesId: Int): LiveData<ApiResponse<CreditsResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<CreditsResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            delay(SERVICE_LATENCY_IN_MILLIS)

            try {
                val postsRequest = ApiConfig.getApiService().getDirectorMovies(moviesId, APP_ID)
                val postsResponse = postsRequest.await()

                Log.e(TAG, "cek postResponse director: ${postsResponse}")
                resultMovie.postValue(ApiResponse.success(postsResponse))

                EspressoIdlingResource.decrement()
            } catch (ex: Exception) {
                Log.e(TAG, "Error response director: "+ ex.message)
            }
        }
        return resultMovie
    }



}