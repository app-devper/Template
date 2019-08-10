package com.devper.template.movie

import androidx.lifecycle.LiveData
import androidx.paging.toLiveData
import com.devper.common.api.Resource
import com.devper.common.networkThread
import com.devper.common.toResource
import com.devper.template.common.AppDatabase
import com.devper.template.common.model.PagedListResult
import com.devper.template.movie.data.MovieDataSourceFactory
import com.devper.template.movie.model.Configuration
import com.devper.template.movie.model.Movie

class MovieRepository(val db: AppDatabase, private val service: MovieService) {
    private val apiKey: String = "3fa9058382669f72dcb18fb405b7a831"

    fun getMovies(): PagedListResult<Movie> {
        val dataSourceFactory = MovieDataSourceFactory(apiKey, service)
        val result = dataSourceFactory.toLiveData(pageSize = 20, fetchExecutor = networkThread())
        return PagedListResult(result, dataSourceFactory.isInitialLoading, dataSourceFactory.networkState)
    }

    fun getConfiguration(): LiveData<Resource<Configuration>> {
        return service.getConfiguration(apiKey).toResource()
    }

}