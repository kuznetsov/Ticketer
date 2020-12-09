package com.elliotgrin.ticketer.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elliotgrin.ticketer.model.CityUiModel

class MainViewModel : ViewModel() {

    var departureCity: CityUiModel? = null
        set(value) {
            field = value
            _citiesLiveData.value = bothCitiesAreNotNull()
        }

    var arrivalCity: CityUiModel? = null
        set(value) {
            field = value
            _citiesLiveData.value = bothCitiesAreNotNull()
        }

    /**
     * Emits true if both cities are set
     * Emits false if at least one is null
     */
    private val _citiesLiveData = MutableLiveData(bothCitiesAreNotNull())
    val citiesLiveData: LiveData<Boolean> = _citiesLiveData

    private fun bothCitiesAreNotNull() = departureCity != null && arrivalCity != null

}
