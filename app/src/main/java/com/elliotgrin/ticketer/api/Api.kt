package com.elliotgrin.ticketer.api

import com.elliotgrin.ticketer.model.AutocompleteResult
import retrofit2.http.GET
import retrofit2.http.Query

private const val AUTOCOMPLETE = "autocomplete"

interface Api {
    @GET(AUTOCOMPLETE)
    suspend fun getAutocompleteResult(
        @Query("term") term: String,
        @Query("lang") lang: String
    ): AutocompleteResult
}
