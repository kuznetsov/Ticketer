package com.elliotgrin.ticketer.util.ext

import android.view.WindowManager
import androidx.fragment.app.Fragment

fun Fragment.enableFullscreen() = activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

fun Fragment.disableFullscreen() = activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
