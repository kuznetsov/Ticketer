package com.elliotgrin.ticketer.util

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.elliotgrin.ticketer.common.PLANE_ANIMATION_STEP_DURATION_MS

object AnimationUtils {

    fun planeAnimator(): ValueAnimator {
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = PLANE_ANIMATION_STEP_DURATION_MS
        valueAnimator.interpolator = LinearInterpolator()
        return valueAnimator
    }

}
