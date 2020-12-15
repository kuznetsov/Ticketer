package com.elliotgrin.ticketer.map

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.common.PLANE_ANIMATION_STEP_DURATION_MS
import com.elliotgrin.ticketer.main.MainViewModel
import com.elliotgrin.ticketer.model.CityMarker
import com.elliotgrin.ticketer.util.AnimationUtils
import com.elliotgrin.ticketer.util.MapMarkerUtil
import com.elliotgrin.ticketer.util.MapUtils
import com.elliotgrin.ticketer.util.ext.disableFullscreen
import com.elliotgrin.ticketer.util.ext.dpToPx
import com.elliotgrin.ticketer.util.ext.enableFullscreen
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.map_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val PATTERN_GAP_LENGTH_PX = 15f
private const val POLYLINE_WIDTH_PX = 15f
private const val KEY_PREVIOUS_LAT_LNG = "key:previous_lat_lng"
private const val KEY_CURRENT_LAT_LNG = "key:current_lat_lng"
private const val KEY_I_STEP = "key:i_step"

class MapFragment(
    private val mapMarkerUtil: MapMarkerUtil
) : Fragment(R.layout.map_fragment), OnMapReadyCallback, MapViewProvider {

    private val sharedViewModel: MainViewModel by sharedViewModel()

    override fun getMapView(): MapView? = mapView

    private var currentLatLng: LatLng? = null
    private var previousLatLng: LatLng? = null
    private var i: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.registerFragmentLifecycleCallbacks(
            MapViewFragmentLifecycleCallback,
            false
        )

        savedInstanceState?.let { restoreLatLng(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.getMapAsync(this)
        enableFullscreen()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putParcelable(KEY_PREVIOUS_LAT_LNG, previousLatLng)
            putParcelable(KEY_CURRENT_LAT_LNG, currentLatLng)
            putInt(KEY_I_STEP, i)
        }
    }

    /**
     * Here the whole work happens
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: run {
            Toast.makeText(requireContext(), "Error loading Google Map", Toast.LENGTH_SHORT).show()
            return
        }

        val departure = CityMarker(sharedViewModel.departureCity ?: return)
        val arrival = CityMarker(sharedViewModel.arrivalCity ?: return)
        val points = MapUtils.getBezierCurvePoints(googleMap, departure.location, arrival.location)

        setupMap(googleMap)
        moveMap(googleMap, departure, arrival)
        addCityMarkers(googleMap, departure, arrival)
        drawCurvePolyline(googleMap, points)
        animatePlaneMarker(googleMap, points)
    }

    private fun restoreLatLng(savedInstanceState: Bundle) {
        previousLatLng = savedInstanceState.getParcelable(KEY_PREVIOUS_LAT_LNG)
        currentLatLng = savedInstanceState.getParcelable(KEY_CURRENT_LAT_LNG)
        i = savedInstanceState.getInt(KEY_I_STEP)
    }

    private fun setupMap(googleMap: GoogleMap) {
        googleMap.uiSettings.isRotateGesturesEnabled = false
    }

    private fun addCityMarkers(googleMap: GoogleMap, departure: CityMarker, arrival: CityMarker) {
        val departureMarker = mapMarkerUtil.createCityMarker(departure)
        val arrivalMarker = mapMarkerUtil.createCityMarker(arrival)

        googleMap.apply {
            addMarker(departureMarker)
            addMarker(arrivalMarker)
        }
    }

    private fun moveMap(googleMap: GoogleMap, departure: CityMarker, arrival: CityMarker) {
        val bounds = LatLngBounds.Builder().run {
            include(departure.location)
            include(arrival.location)
            build()
        }

        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50.dpToPx())
        googleMap.moveCamera(cameraUpdate)
    }

    private fun drawCurvePolyline(googleMap: GoogleMap, points: List<LatLng>) {
        val polylineOptions = PolylineOptions().apply {
            addAll(points)
            width(POLYLINE_WIDTH_PX)
            color(ContextCompat.getColor(requireContext(), R.color.gray_900_50))
            pattern(listOf(Dot(), Gap(PATTERN_GAP_LENGTH_PX)))
        }

        googleMap.addPolyline(polylineOptions)
    }

    private fun animatePlaneMarker(googleMap: GoogleMap, points: List<LatLng>) {
        if (currentLatLng == null) currentLatLng = points.first()
        val markerOptions = mapMarkerUtil.createPlaneMarker(currentLatLng!!)
        val planeMarker = googleMap.addMarker(markerOptions)
        val rotation = if (currentLatLng != null && previousLatLng != null) {
            MapUtils.getRotation(previousLatLng!!, currentLatLng!!)
        } else {
            MapUtils.getRotation(points[0], points[1])
        }
        planeMarker?.rotation = rotation

        val handler = Handler()
        var runnable = Runnable { }
        runnable = Runnable {
            if (i < points.size) {
                updatePlaneLocation(planeMarker, points[i])
                handler.postDelayed(runnable, PLANE_ANIMATION_STEP_DURATION_MS)
                i++
            } else {
                handler.removeCallbacks(runnable)
            }
        }

        val delay = if (i == 1) 2000 else 0L
        handler.postDelayed(runnable, delay)
    }

    private fun updatePlaneLocation(planeMarker: Marker, latLng: LatLng) {
        previousLatLng = currentLatLng
        currentLatLng = latLng

        val valueAnimator = AnimationUtils.planeAnimator()
        valueAnimator.addUpdateListener { animator ->
            val multiplier = animator.animatedFraction
            val nextLocation = LatLng(
                multiplier * currentLatLng!!.latitude + (1 - multiplier) * previousLatLng!!.latitude,
                multiplier * currentLatLng!!.longitude + (1 - multiplier) * previousLatLng!!.longitude
            )
            val rotation = MapUtils.getRotation(previousLatLng!!, nextLocation)

            planeMarker.apply {
                if (!rotation.isNaN()) this.rotation = rotation
                position = nextLocation
            }
        }

        valueAnimator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disableFullscreen()
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.unregisterFragmentLifecycleCallbacks(MapViewFragmentLifecycleCallback)
    }

}
