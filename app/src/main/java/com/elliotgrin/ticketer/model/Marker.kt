package com.elliotgrin.ticketer.model

import com.google.android.gms.maps.model.LatLng

data class Marker(
    val location: LatLng,
    val title: String
) {

    constructor(cityUiModel: CityUiModel) : this(
        location = cityUiModel.location,
        title = cityUiModel.shortName.toString()
    )

}
