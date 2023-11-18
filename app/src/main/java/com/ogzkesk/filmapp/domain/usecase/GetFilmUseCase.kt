package com.ogzkesk.filmapp.domain.usecase

import com.ogzkesk.filmapp.domain.repository.FilmRepository
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(private val repository: FilmRepository) {

    operator fun invoke(id: String) = repository.getFilmById(id)
}