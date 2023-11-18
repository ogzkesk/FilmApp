package com.ogzkesk.filmapp.domain.usecase

import com.ogzkesk.filmapp.domain.repository.FilmRepository
import javax.inject.Inject

class SearchFilmUseCase @Inject constructor(private val repository: FilmRepository){

    operator fun invoke(query: String) = repository.searchFilm(query)

}