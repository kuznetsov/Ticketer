package com.elliotgrin.ticketer.util.ext

import android.content.res.Resources

/**
 * Converts Int Dp value to Px value
 */
fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
