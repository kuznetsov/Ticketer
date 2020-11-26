package com.elliotgrin.ticketer.search

import com.elliotgrin.ticketer.api.Api
import com.elliotgrin.ticketer.model.CityUiModel

class AutocompleteRepository(private val api: Api) {

    fun requestAutocomplete(term: String, lang: String): List<CityUiModel> {
        val result = api.getAutocompleteResult(term, lang)
        return result.cities.map(::CityUiModel)
    }

}
