package com.elliotgrin.ticketer.main

import androidx.lifecycle.ViewModel
import com.elliotgrin.ticketer.model.CityUiModel

class MainViewModel : ViewModel() {

    var departureCity: CityUiModel? = null
    var arrivalCity: CityUiModel? = null

}
