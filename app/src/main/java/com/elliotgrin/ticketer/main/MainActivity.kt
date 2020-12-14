package com.elliotgrin.ticketer.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.search.SearchFragment
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // Shared ViewModel to store departure and arrival city objects
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) openSearchFragment()
    }

    private fun openSearchFragment() = supportFragmentManager.commit {
        add<SearchFragment>(R.id.mainContainer)
    }

}
