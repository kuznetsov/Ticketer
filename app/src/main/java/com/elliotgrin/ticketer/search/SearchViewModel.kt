package com.elliotgrin.ticketer.search

import androidx.lifecycle.ViewModel
import com.elliotgrin.ticketer.model.CityUiModel

class SearchViewModel(private val repository: AutocompleteRepository) : ViewModel(), AutocompleteRequestHelper {

    // AutocompleteRequestHelper

    override fun requestCity(term: String): List<CityUiModel> {
        return repository.requestAutocomplete(term, "RU")
    }

}
