package com.elliotgrin.ticketer.util

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator

object AnimationUtils {

    fun planeAnimator(): ValueAnimator {
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 5000
        valueAnimator.interpolator = LinearInterpolator()
        return valueAnimator
    }

}
