package com.anandgaur.hcltask.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anandgaur.hcltask.data.remote.ApiResponse
import com.anandgaur.hcltask.data.remote.StatusResponse
import com.anandgaur.hcltask.utils.AppExecutors
import com.anandgaur.hcltask.valueobject.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    protected fun onFetchFailed() {}

    // loadFromDB() yang berfungsi untuk mengakses data dari local dataabse
    protected abstract fun loadFromDB(): LiveData<ResultType>

    // shouldFetch untuk mengetahui apakah perlu akses remote database atau tidak
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    // createCall untuk mengakses remote database
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?

    // saveCallResult untuk menyimpan data hasil dari remote database ke local database
    protected abstract fun saveCallResult(data: RequestType)


    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall() as LiveData<ApiResponse<RequestType>>

        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCESS ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.body)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                StatusResponse.EMPTY -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = Resource.success(newData)
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}