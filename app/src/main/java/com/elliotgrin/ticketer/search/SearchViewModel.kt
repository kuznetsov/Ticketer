package com.elliotgrin.ticketer.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elliotgrin.ticketer.model.CityUiModel
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: AutocompleteRepository) : ViewModel(), AutocompleteRequestHelper {

    // AutocompleteRequestHelper

    override fun requestCity(term: String): List<CityUiModel> {
        // TODO: 27.11.2020 Add language support
        return repository.requestAutocomplete(term, "RU")
    }

}
