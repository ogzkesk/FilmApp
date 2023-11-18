package com.ogzkesk.filmapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.filmapp.data.dto.Search
import com.ogzkesk.filmapp.domain.model.SearchModel
import com.ogzkesk.filmapp.domain.usecase.SearchFilmUseCase
import com.ogzkesk.filmapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val INITIAL_SEARCH = "batman"
private const val TYPE_MOVIE = "movie"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val search: SearchFilmUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<SearchModel>>(Resource.Loading)
    val uiState: StateFlow<Resource<SearchModel>> get() = _uiState

    private val _types = MutableStateFlow<Pair<List<Search?>,List<Search?>>?>(null)
    val types : StateFlow<Pair<List<Search?>,List<Search?>>?> get() = _types

    fun onSearch(query: String) {
        if(query.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            search(query).collect { resource ->
                if(resource is Resource.Success){
                    _types.value = resource.data.search?.partition { it?.type == TYPE_MOVIE }
                }

                _uiState.update { resource }
            }
        }
    }


    init {
        onSearch(INITIAL_SEARCH)
    }
}