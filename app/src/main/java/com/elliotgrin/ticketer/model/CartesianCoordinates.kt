package com.elliotgrin.ticketer.model

import com.google.android.gms.maps.model.LatLng
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

data class CartesianCoordinates(
    val x: Double,
    val y: Double,
    val z: Double
) {

    constructor(p: CartesianCoordinates) : this(p.x, p.y, p.z)

    constructor(latLng: LatLng) : this(fromLatLng(latLng.latitude, latLng.longitude))

    companion object {

        private const val R = 6371 // approximate radius of earth

        fun toLatLng(x: Double, y: Double, z: Double) = LatLng(
            Math.toDegrees(asin(z / R)),
            Math.toDegrees(atan2(y, x))
        )

        private fun fromLatLng(lat: Double, lng: Double): CartesianCoordinates {
            val radLat = Math.toRadians(lat)
            val radLng = Math.toRadians(lng)

            val x = R * cos(radLat) * cos(radLng)
            val y = R * cos(radLat) * sin(radLng)
            val z = R * sin(radLat)

            return CartesianCoordinates(x, y, z)
        }

    }

}
