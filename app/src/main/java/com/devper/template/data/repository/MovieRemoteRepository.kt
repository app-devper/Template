package com.devper.template.data.repository

import com.devper.template.data.remote.AppService
import com.devper.template.data.session.AppSession
import com.devper.template.domain.model.movie.Configuration
import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.model.movie.Movies
import com.devper.template.domain.repository.MovieRepository

class MovieRemoteRepository(private val api: AppService, private val session: AppSession) : MovieRepository {

    override suspend fun getMovies(page: Int): Movies {
        val mapper = MovieMapper()
        val config = getConfiguration()
        return api.getMovies(page).let {
            mapper.toMoviesDomain(it, config.images)
        }
    }

    override suspend fun getConfiguration(): Configuration {
        val mapper = MovieMapper()
        val result = session.configurationData ?: api.getConfiguration()
        return result.let {
            session.configurationData = it
            mapper.toConfigurationDomain(it)
        }
    }

    override suspend fun getMovie(id: Int): Movie {
        val mapper = MovieMapper()
        val config = getConfiguration()
        return api.getMovie(id).let {
            mapper.toMovieDomain(it, config.images)
        }
    }

}