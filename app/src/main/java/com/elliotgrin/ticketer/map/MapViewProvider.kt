package com.elliotgrin.ticketer.map

import com.google.android.gms.maps.MapView

interface MapViewProvider {

  fun getMapView(): MapView?

}
