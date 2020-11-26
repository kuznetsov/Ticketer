package com.elliotgrin.ticketer.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes id: Int, attachToRoot: Boolean = false): View =
  LayoutInflater.from(context).inflate(id, this, attachToRoot)
