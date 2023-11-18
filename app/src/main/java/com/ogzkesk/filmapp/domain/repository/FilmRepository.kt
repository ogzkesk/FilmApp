package com.ogzkesk.filmapp.domain.repository

import com.ogzkesk.filmapp.domain.model.FilmModel
import com.ogzkesk.filmapp.domain.model.SearchModel
import com.ogzkesk.filmapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    fun searchFilm(query: String) : Flow<Resource<SearchModel>>

    fun getFilmById(id: String) : Flow<Resource<FilmModel>>
}