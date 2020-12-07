package com.elliotgrin.ticketer.util

import com.elliotgrin.ticketer.model.CartesianCoordinates
import com.google.android.gms.maps.model.LatLng
import kotlin.math.*

private const val SHIFT = 8

object MapUtils {

    fun getBezierCurvePoints(from: LatLng, to: LatLng): List<LatLng> {
//        val p1 = if (from.longitude < to.longitude) from else to
//        val p2 = if (from.longitude > to.longitude) from else to

        val result = mutableListOf(from)

        val middle = midPoint(from, to)

        val control1 = getControlPoint1(midPoint(from, middle))
        val control2 = getControlPoint2(midPoint(middle, to))

        val p1 = CartesianCoordinates(from)
        val p2 = CartesianCoordinates(control1)
        val p3 = CartesianCoordinates(control2)
        val p4 = CartesianCoordinates(to)

        var t = 0.0
        val delta = 0.05

        while (t < 1.0) {
            val x = bezierStep(p1.x, p2.x, p3.x, p4.x, t)
            val y = bezierStep(p1.y, p2.y, p3.y, p4.y, t)
            val z = bezierStep(p1.z, p2.z, p3.z, p4.z, t)

            val control: LatLng = CartesianCoordinates.toLatLng(x, y, z)

            result.add(control)

            t += delta
        }

        return result + to
    }

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

    private fun midPoint(p1: LatLng, p2: LatLng): LatLng {
        val lat1 = Math.toRadians(p1.latitude)
        val lon1 = Math.toRadians(p1.longitude)
        val lat2 = Math.toRadians(p2.latitude)
        val lon2 = Math.toRadians(p2.longitude)

        val x1 = cos(lat1) * cos(lon1)
        val y1 = cos(lat1) * sin(lon1)
        val z1 = sin(lat1)
        val x2 = cos(lat2) * cos(lon2)
        val y2 = cos(lat2) * sin(lon2)
        val z2 = sin(lat2)

        val x = (x1 + x2) / 2
        val y = (y1 + y2) / 2
        val z = (z1 + z2) / 2

        val lon = atan2(y, x)
        val hyp = sqrt(x * x + y * y)
        val lat = atan2(z, hyp)
//
//        // HACK: 0.9 and 1.1 was found by trial and error; this is probably *not* the right place to apply mid point shifting
//        var lat = atan2(0.9 * z, hyp)
//        if (lat > 0) lat = atan2(1.1 * z, hyp)

        return LatLng(Math.toDegrees(lat), Math.toDegrees(lon))
    }

    private fun getControlPoint1(controlPoint: LatLng) = LatLng(
        controlPoint.latitude - SHIFT,
        controlPoint.longitude + SHIFT
    )

    private fun getControlPoint2(controlPoint: LatLng) = LatLng(
        controlPoint.latitude + SHIFT,
        controlPoint.longitude - SHIFT
    )

    // P = (1−t)^3P1 + 3(1−t)^2tP2 + 3(1−t)t^2P3 + t^3P4
    private fun bezierStep(p1: Double, p2: Double, p3: Double, p4: Double, t: Double): Double {
        val oneMinusT = 1 - t
        return oneMinusT.pow(3) * p1 + 3 * oneMinusT.pow(2) * t * p2 + 3 * oneMinusT * t.pow(2) * p3 + t.pow(3) * p4
    }


}
