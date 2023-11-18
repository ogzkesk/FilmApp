package com.ogzkesk.filmapp.di

import com.ogzkesk.filmapp.data.repository.FilmRepositoryImpl
import com.ogzkesk.filmapp.domain.repository.FilmRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindFilmRepository(
        filmRepositoryImpl: FilmRepositoryImpl
    ): FilmRepository
}