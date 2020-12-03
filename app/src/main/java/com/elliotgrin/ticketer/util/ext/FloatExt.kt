package com.elliotgrin.ticketer.util.ext

import android.content.res.Resources
import android.util.TypedValue

fun Float.dpToPx() = this * Resources.getSystem().displayMetrics.density

fun Float.spToPx() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics
)
