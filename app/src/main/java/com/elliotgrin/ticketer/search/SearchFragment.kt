package com.elliotgrin.ticketer.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.elliotgrin.ticketer.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment(private val viewModel: SearchViewModel) : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val departureSuggestionsAdapter = SuggestionsAdapter(viewModel) { }
        editTextDeparture.threshold = 2
        editTextDeparture.setAdapter(departureSuggestionsAdapter)
    }

}
