package com.ogzkesk.filmapp.data

import com.ogzkesk.filmapp.data.dto.FilmDTO
import com.ogzkesk.filmapp.data.dto.SearchDTO
import com.ogzkesk.filmapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {

    @GET("?")
    suspend fun searchFilm(
        @Query("s") query: String,
        @Query("apikey") apiKey : String = Constants.API_KEY
    ): SearchDTO

    @GET("?")
    suspend fun getFilmById(
        @Query("i") id: String,
        @Query("apikey") apiKey : String = Constants.API_KEY
    ): FilmDTO

}