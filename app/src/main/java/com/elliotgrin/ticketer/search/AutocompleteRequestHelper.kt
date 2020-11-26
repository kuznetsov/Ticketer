package com.elliotgrin.ticketer.search

import com.elliotgrin.ticketer.model.CityUiModel

interface AutocompleteRequestHelper {
    fun requestCity(term: String): List<CityUiModel>
}
