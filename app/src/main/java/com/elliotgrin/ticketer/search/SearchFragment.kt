package com.elliotgrin.ticketer.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.elliotgrin.ticketer.R

class SearchFragment(private val viewModel: SearchViewModel) : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

    }

}
