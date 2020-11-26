package com.elliotgrin.ticketer.model

import com.google.android.gms.maps.model.LatLng

data class CityUiModel(
    val id: Int,
    val fullName: String,
    val shortName: String,
    val location: LatLng
) {

    constructor(city: City) : this(
        id = city.id,
        fullName = city.fullname,
        shortName = city.iata.first(),
        location = LatLng(city.location.lat, city.location.lon)
    )

}
