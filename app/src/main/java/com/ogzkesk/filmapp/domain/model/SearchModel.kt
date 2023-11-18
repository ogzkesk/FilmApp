package com.ogzkesk.filmapp.domain.model

import com.ogzkesk.filmapp.data.dto.Search

data class SearchModel(
    val response: String?,
    val search: List<Search?>?,
    val totalResults: String?
)
