package com.elliotgrin.ticketer.search

import com.elliotgrin.ticketer.api.Api
import com.elliotgrin.ticketer.model.CityUiModel
import kotlinx.coroutines.runBlocking

class AutocompleteRepository(private val api: Api) {

    // TODO: 30.11.2020 use suspend or coroutineContext
    fun requestAutocomplete(term: String, lang: String): List<CityUiModel> = runBlocking {
        val result = api.getAutocompleteResult(term, lang)
        result.cities.map(::CityUiModel)
    }

}
