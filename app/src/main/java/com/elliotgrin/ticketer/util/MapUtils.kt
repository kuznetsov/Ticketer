package com.elliotgrin.ticketer.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.abs
import kotlin.math.atan

object MapUtils {

    fun getRotation(start: LatLng, end: LatLng): Float {
        val latDifference: Double = abs(start.latitude - end.latitude)
        val lngDifference: Double = abs(start.longitude - end.longitude)
        return when {
            start.latitude < end.latitude && start.longitude < end.longitude -> {
                Math.toDegrees(atan(lngDifference / latDifference)).toFloat()
            }
            start.latitude >= end.latitude && start.longitude < end.longitude -> {
                (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 90).toFloat()
            }
            start.latitude >= end.latitude && start.longitude >= end.longitude -> {
                (Math.toDegrees(atan(lngDifference / latDifference)) + 180).toFloat()
            }
            start.latitude < end.latitude && start.longitude >= end.longitude -> {
                (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 270).toFloat()
            }
            else -> Float.NaN
        }
    }

}
