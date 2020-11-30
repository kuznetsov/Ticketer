package com.elliotgrin.ticketer.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object MapViewFragmentLifecycleCallback : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentActivityCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        (f as? MapViewProvider)?.getMapView()?.onCreate(savedInstanceState)
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        (f as? MapViewProvider)?.getMapView()?.onSaveInstanceState(outState)
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        (f as? MapViewProvider)?.getMapView()?.onResume()
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        (f as? MapViewProvider)?.getMapView()?.onStart()
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        (f as? MapViewProvider)?.getMapView()?.onPause()
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        (f as? MapViewProvider)?.getMapView()?.onStop()
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        (f as? MapViewProvider)?.getMapView()?.onDestroy()
    }

}
