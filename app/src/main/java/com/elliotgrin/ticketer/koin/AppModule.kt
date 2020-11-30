package com.elliotgrin.ticketer.koin

import com.elliotgrin.ticketer.main.MainViewModel
import com.elliotgrin.ticketer.map.MapFragment
import com.elliotgrin.ticketer.map.MapViewModel
import com.elliotgrin.ticketer.search.AutocompleteRepository
import com.elliotgrin.ticketer.search.SearchFragment
import com.elliotgrin.ticketer.search.SearchViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentsModule = module {
    fragment { SearchFragment(get()) }
    fragment { MapFragment(get()) }
}

val viewModelsModule = module {
    viewModel { MainViewModel() }
    viewModel { SearchViewModel(get()) }
    viewModel { MapViewModel() }
}

val repositoriesModule = module {
    factory { AutocompleteRepository(get()) }
}
