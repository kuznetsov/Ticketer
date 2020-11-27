package com.elliotgrin.ticketer.koin

import com.elliotgrin.ticketer.search.AutocompleteRepository
import com.elliotgrin.ticketer.search.SearchFragment
import com.elliotgrin.ticketer.search.SearchViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentsModule = module {
    fragment { SearchFragment(get()) }
}

val viewModelsModule = module {
    viewModel { SearchViewModel(get()) }
}

val repositoriesModule = module {
    factory { AutocompleteRepository(get()) }
}
