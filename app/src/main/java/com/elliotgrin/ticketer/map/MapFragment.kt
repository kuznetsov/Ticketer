package com.elliotgrin.ticketer.map

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.main.MainViewModel
import com.elliotgrin.ticketer.model.Marker
import com.elliotgrin.ticketer.util.MapMarkerUtil
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
        val departure = Marker(sharedViewModel.departureCity ?: return)
        val arrival = Marker(sharedViewModel.arrivalCity ?: return)
        setupMap(googleMap)
        setMarkers(googleMap, departure, arrival)
        drawPlanePath(googleMap, departure, arrival)
    }

    private fun setupMap(googleMap: GoogleMap?) {
        googleMap?.uiSettings?.isRotateGesturesEnabled = false
    }

    // TODO: 02.12.2020 fix anchors and dim markers
    private fun setMarkers(googleMap: GoogleMap?, departure: Marker, arrival: Marker) {
        val bitmap1 = mapMarkerUtil.createBitmapFromMarker(departure)
        val bitmap2 = mapMarkerUtil.createBitmapFromMarker(arrival)

        val bm1 = BitmapDescriptorFactory.fromBitmap(bitmap1)
        val bm2 = BitmapDescriptorFactory.fromBitmap(bitmap2)

        val departureMarker =
            MarkerOptions()
                .position(departure.location)
                .icon(bm1)
                .anchor(0.5f, 0.5f)

        val arrivalMarker = MarkerOptions()
            .position(arrival.location)
            .icon(bm2)
            .anchor(0.5f, 0.5f)

        googleMap?.apply {
            addMarker(departureMarker)
            addMarker(arrivalMarker)
        }
    }

    private fun moveMap(googleMap: GoogleMap, departure: Marker, arrival: Marker) {
        // TODO: 02.12.2020 move map in center between two markers
    }

    private fun drawPlanePath(googleMap: GoogleMap?, departure: Marker, arrival: Marker) {
        val polylineOptions = PolylineOptions().apply {
            add(departure.location)
            add(arrival.location)
            geodesic(true)
            width(POLYLINE_WIDTH_PX)
            color(ContextCompat.getColor(requireContext(), R.color.gray_900_50))
            pattern(listOf(Dot(), Gap(PATTERN_GAP_LENGTH_PX)))
        }

        googleMap?.addPolyline(polylineOptions)
    }

}

