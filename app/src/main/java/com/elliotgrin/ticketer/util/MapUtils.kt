package com.elliotgrin.ticketer.util

import android.graphics.Point
import android.graphics.PointF
import androidx.core.graphics.toPoint
import androidx.core.graphics.toPointF
import com.github.ajalt.timberkt.d
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.*

private const val SHIFT_PX = 75

// TODO: 08.12.2020 get rid of comments
object MapUtils {

    fun getBezierCurvePoints(
        googleMap: GoogleMap?,
        from: LatLng,
        to: LatLng
    ): List<LatLng> {
        val projection = googleMap?.projection ?: return listOf()
        val p1 = projection.toScreenLocation(from)
        val p4 = projection.toScreenLocation(to)
        val middle = midPoint(p1, p4)
        val p2 = getControlPoint1(midPoint(p1, middle))
        val p3 = getControlPoint2(midPoint(middle, p4))
        val midLatLng = projection.fromScreenLocation(middle)

        val c1LatLng = projection.fromScreenLocation(p2)
        val c2LatLng = projection.fromScreenLocation(p3)
        googleMap.addMarker(MarkerOptions().position(midLatLng).title("Middle"))
        googleMap.addMarker(MarkerOptions().position(c1LatLng).title("Control 1"))
        googleMap.addMarker(MarkerOptions().position(c2LatLng).title("Control 2"))
        d { "mid - $p2, shifted - ${getControlPoint1(p2)}" }
        d { "mid - $p3, shifted - ${getControlPoint1(p3)}" }

        val result = mutableListOf<LatLng>()

        val delta = 0.05.toBigDecimal()
        var t = 0.toBigDecimal()

        while (t <= 1.toBigDecimal()) {

            d { "t: $t" }

            val tFloat = t.toFloat()

            val pf1 = p1.toPointF()
            val pf2 = p2.toPointF()
            val pf3 = p3.toPointF()
            val pf4 = p4.toPointF()

            val x = bezierStep(pf1.x, pf2.x, pf3.x, pf4.x, tFloat)
            val y = bezierStep(pf1.y, pf2.y, pf3.y, pf4.y, tFloat)
            val point = PointF(x, y).toPoint()

            val control: LatLng = projection.fromScreenLocation(point)
            d { "PointF: ${PointF(x, y)}, Point: $point" }

            result += control

            t += delta
        }

        return result
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

    private fun midPoint(p1: Point, p2: Point): Point {
        val x = (p1.x + p2.x) / 2
        val y = (p1.y + p2.y) / 2

        return Point(x, y)
    }

    private fun getControlPoint1(controlPoint: Point) = Point(
        controlPoint.x - SHIFT_PX,
        controlPoint.y - SHIFT_PX
    )

    private fun getControlPoint2(controlPoint: Point) = Point(
        controlPoint.x + SHIFT_PX,
        controlPoint.y + SHIFT_PX
    )

    // P = (1−t)^3P1 + 3(1−t)^2tP2 + 3(1−t)t^2P3 + t^3P4
    private fun bezierStep(p1: Float, p2: Float, p3: Float, p4: Float, t: Float): Float {
        val oneMinusT = 1 - t
        return oneMinusT.pow(3) * p1 + 3 * oneMinusT.pow(2) * t * p2 + 3 * oneMinusT * t.pow(2) * p3 + t.pow(3) * p4
    }

}
