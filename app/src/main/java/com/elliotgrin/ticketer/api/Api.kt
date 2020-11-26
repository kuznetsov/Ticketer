package com.elliotgrin.ticketer.api

import com.elliotgrin.ticketer.model.AutocompleteResult

interface Api {
    fun getAutocompleteResult(term: String, lang: String): AutocompleteResult
}
