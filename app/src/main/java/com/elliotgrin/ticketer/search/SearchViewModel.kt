package com.elliotgrin.ticketer.search

import androidx.lifecycle.ViewModel
import com.elliotgrin.ticketer.common.LANG_EN
import com.elliotgrin.ticketer.common.LANG_RU
import com.elliotgrin.ticketer.model.CityUiModel

class SearchViewModel(private val repository: AutocompleteRepository) : ViewModel(), AutocompleteRequestHelper {

    private var inputLanguage = LANG_EN

    fun setLanguage(text: CharSequence?) {
        inputLanguage = when {
            text?.all { char -> char.isLetter() && char in 'A'..'z' } == true -> LANG_EN
            // This range contains letters only
            text?.all { char -> char in 'А'..'я' } == true -> LANG_RU
            else -> LANG_EN
        }
    }

    // AutocompleteRequestHelper

    override fun requestCity(term: String): List<CityUiModel> {
        return repository.requestAutocomplete(term, inputLanguage)
    }

}
