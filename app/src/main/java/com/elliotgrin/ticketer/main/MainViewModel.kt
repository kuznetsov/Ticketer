package com.elliotgrin.ticketer.main

import androidx.lifecycle.ViewModel
import com.elliotgrin.ticketer.model.CityUiModel
import com.github.ajalt.timberkt.d
import com.google.android.gms.maps.model.LatLng

class MainViewModel : ViewModel() {

    var departureCity: CityUiModel? = CityUiModel(id=12196, fullName="Санкт-Петербург, Россия", shortName="LED", location=LatLng(59.95,30.316667))
    var arrivalCity: CityUiModel? = CityUiModel(id=13559, fullName="Рим, Италия", shortName="ROM", location=LatLng(41.890202,12.492214))

}
