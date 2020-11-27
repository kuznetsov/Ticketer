package com.elliotgrin.ticketer.util

import android.widget.AutoCompleteTextView

fun AutoCompleteTextView.setTextAndDismissDropDown(text: String) {
    setText(text)
    setSelection(text.length)
    dismissDropDown()
}
