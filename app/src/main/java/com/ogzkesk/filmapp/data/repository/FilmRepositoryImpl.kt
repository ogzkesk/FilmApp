package com.ogzkesk.filmapp.data.repository

import com.ogzkesk.filmapp.data.FilmService
import com.ogzkesk.filmapp.data.mapper.toFilmModel
import com.ogzkesk.filmapp.data.mapper.toSearchModel
import com.ogzkesk.filmapp.domain.model.FilmModel
import com.ogzkesk.filmapp.domain.model.SearchModel
import com.ogzkesk.filmapp.domain.repository.FilmRepository
import com.ogzkesk.filmapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val service: FilmService
) : FilmRepository{

    override fun searchFilm(query: String): Flow<Resource<SearchModel>> {
        return flow {

            emit(Resource.Loading)
            try {
                emit(Resource.Success(service.searchFilm(query).toSearchModel()))

            } catch (e: HttpException){
                emit(Resource.Error(e))
            }
        }
    }


    override fun getFilmById(id: String): Flow<Resource<FilmModel>> {
        return flow {

            emit(Resource.Loading)
            try {
                emit(Resource.Success(service.getFilmById(id).toFilmModel()))

            } catch (e: HttpException){
                emit(Resource.Error(e))
            }
        }
    }
}