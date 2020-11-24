package com.elliotgrin.ticketer.koin

import com.elliotgrin.ticketer.search.SearchFragment
import com.elliotgrin.ticketer.search.SearchViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    viewModel { SearchViewModel() }
    // Fragments
    fragment { SearchFragment(get()) }
}
