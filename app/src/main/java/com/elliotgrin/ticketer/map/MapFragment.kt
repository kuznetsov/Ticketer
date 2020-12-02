package com.elliotgrin.ticketer.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.main.MainViewModel
import com.elliotgrin.ticketer.model.Marker
import com.elliotgrin.ticketer.util.MapMarkerUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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
        setMarkers(googleMap, departure, arrival)
    }

    private fun setMarkers(googleMap: GoogleMap?, departure: Marker, arrival: Marker) {
        val bitmap1 = mapMarkerUtil.createBitmapFromMarker(departure)
        val bitmap2 = mapMarkerUtil.createBitmapFromMarker(arrival)

        val bm1 = BitmapDescriptorFactory.fromBitmap(bitmap1)
        val bm2 = BitmapDescriptorFactory.fromBitmap(bitmap2)

        val departureMarker = MarkerOptions().position(departure.location).icon(bm1)
        val arrivalMarker = MarkerOptions().position(arrival.location).icon(bm2)

        googleMap?.apply {
            addMarker(departureMarker)
            addMarker(arrivalMarker)
        }
    }

}
