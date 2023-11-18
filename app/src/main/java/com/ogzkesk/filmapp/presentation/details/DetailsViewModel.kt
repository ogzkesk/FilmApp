package com.ogzkesk.filmapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.filmapp.domain.model.FilmModel
import com.ogzkesk.filmapp.domain.model.SearchModel
import com.ogzkesk.filmapp.domain.usecase.GetFilmUseCase
import com.ogzkesk.filmapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val NO_ID_ERROR = "Something went wrong"

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val getFilm: GetFilmUseCase
) : ViewModel(){


    private val _uiState = MutableStateFlow<Resource<FilmModel>>(Resource.Loading)
    val uiState: StateFlow<Resource<FilmModel>> get() = _uiState


    fun getFilmById(id: String?){
        if (id == null) {
            _uiState.update { Resource.Error(Throwable(NO_ID_ERROR)) }
            return
        }

        viewModelScope.launch {
            getFilm(id).collect { resource ->
                _uiState.update { resource }
            }
        }
    }
}