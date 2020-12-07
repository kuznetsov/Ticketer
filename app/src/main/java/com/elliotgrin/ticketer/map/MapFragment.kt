package com.elliotgrin.ticketer.map

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.common.PLANE_ANIMATION_STEP_DURATION_MS
import com.elliotgrin.ticketer.main.MainViewModel
import com.elliotgrin.ticketer.model.CityMarker
import com.elliotgrin.ticketer.util.AnimationUtils
import com.elliotgrin.ticketer.util.MapMarkerUtil
import com.elliotgrin.ticketer.util.MapUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.map_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val PATTERN_GAP_LENGTH_PX = 15f
private const val POLYLINE_WIDTH_PX = 15f

class MapFragment(
    private val viewModel: MapViewModel,
    private val mapMarkerUtil: MapMarkerUtil
) : Fragment(R.layout.map_fragment), OnMapReadyCallback, MapViewProvider {

    private val sharedViewModel: MainViewModel by sharedViewModel()

    override fun getMapView(): MapView? = mapView

    private var currentLatLng: LatLng? = null
    private var previousLatLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.registerFragmentLifecycleCallbacks(
            MapViewFragmentLifecycleCallback,
            false
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.unregisterFragmentLifecycleCallbacks(MapViewFragmentLifecycleCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val departure = CityMarker(sharedViewModel.departureCity ?: return)
        val arrival = CityMarker(sharedViewModel.arrivalCity ?: return)
        val points = MapUtils.getBezierCurvePoints(departure.location, arrival.location)

        setupMap(googleMap)
        moveMap(googleMap, departure, arrival)
        addCityMarkers(googleMap, departure, arrival)
        drawCurvePolyline(googleMap, points)
        animatePlaneMarker(googleMap, points)
    }

    private fun setupMap(googleMap: GoogleMap?) {
        googleMap?.uiSettings?.isRotateGesturesEnabled = false
    }

    private fun addCityMarkers(googleMap: GoogleMap?, departure: CityMarker, arrival: CityMarker) {
        val departureMarker = mapMarkerUtil.createCityMarker(departure)
        val arrivalMarker = mapMarkerUtil.createCityMarker(arrival)

        googleMap?.apply {
            addMarker(departureMarker)
            addMarker(arrivalMarker)
        }
    }

    private fun moveMap(googleMap: GoogleMap?, departure: CityMarker, arrival: CityMarker) {
        // TODO: 02.12.2020 move map in center between two markers
    }

    private fun drawCurvePolyline(googleMap: GoogleMap?, points: List<LatLng>) {
        val polylineOptions = PolylineOptions().apply {
            addAll(points)
            width(POLYLINE_WIDTH_PX)
            color(ContextCompat.getColor(requireContext(), R.color.gray_900_50))
            pattern(listOf(Dot(), Gap(PATTERN_GAP_LENGTH_PX)))
        }

        googleMap?.addPolyline(polylineOptions)
    }

    private fun animatePlaneMarker(googleMap: GoogleMap?, points: List<LatLng>) {
        val markerOptions = mapMarkerUtil.createPlaneMarker(points.first())
        val planeMarker = googleMap?.addMarker(markerOptions)
        currentLatLng = points.first()

        var i = 1
        val handler = Handler()
        var runnable = Runnable {  }
        runnable = Runnable {
            if (i < points.size) {
                updatePlaneLocation(planeMarker, points[i])
                handler.postDelayed(runnable, PLANE_ANIMATION_STEP_DURATION_MS)
                i++
            } else {
                handler.removeCallbacks(runnable)
            }
        }
        handler.postDelayed(runnable, 2000)
    }

    private fun updatePlaneLocation(planeMarker: Marker?, latLng: LatLng) {
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

            planeMarker?.apply {
                position = nextLocation
                if (!rotation.isNaN()) this.rotation = rotation
            }
        }

        valueAnimator.start()
    }

}
