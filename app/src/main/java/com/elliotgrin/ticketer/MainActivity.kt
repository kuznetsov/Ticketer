package com.elliotgrin.ticketer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elliotgrin.ticketer.search.SearchFragment
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        openSearchFragment()
    }

    private fun openSearchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, SearchFragment::class.java, null)
            .commit()
    }

}
