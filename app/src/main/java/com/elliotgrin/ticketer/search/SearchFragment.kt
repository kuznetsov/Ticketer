package com.elliotgrin.ticketer.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.fragment.app.Fragment
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.model.CityUiModel
import com.elliotgrin.ticketer.util.setTextAndDismissDropDown
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment(private val viewModel: SearchViewModel) : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val departureSuggestionsAdapter = SuggestionsAdapter(viewModel, this::setDeparture)
        val arrivalSuggestionsAdapter = SuggestionsAdapter(viewModel, this::setArrival)
        editTextDeparture.threshold = 2
        editTextArrival.threshold = 2
        editTextDeparture.setAdapter(departureSuggestionsAdapter)
        editTextArrival.setAdapter(arrivalSuggestionsAdapter)
    }

    private fun setDeparture(departure: CityUiModel) {
        editTextDeparture.setTextAndDismissDropDown(departure.shortName.toString())
    }

    private fun setArrival(arrival: CityUiModel) {
        editTextArrival.setTextAndDismissDropDown(arrival.shortName.toString())
    }

}
