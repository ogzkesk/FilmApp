package com.ogzkesk.filmapp.data.mapper

import com.ogzkesk.filmapp.data.dto.FilmDTO
import com.ogzkesk.filmapp.data.dto.SearchDTO
import com.ogzkesk.filmapp.domain.model.FilmModel
import com.ogzkesk.filmapp.domain.model.SearchModel

fun SearchDTO.toSearchModel() : SearchModel {
    return SearchModel(
        response = response,
        search = search,
        totalResults = totalResults
    )
}

fun FilmDTO.toFilmModel() : FilmModel {
    return FilmModel(
        actors = actors,
        country = country,
        genre = genre,
        imdbID = imdbID,
        imdbRating = imdbRating,
        plot = plot,
        poster = poster,
        released = released,
        runtime = runtime,
        title = title,
        type = type
    )
}