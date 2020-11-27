package com.elliotgrin.ticketer.util.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView

fun AutoCompleteTextView.setTextAndDismissDropDown(text: String) {
    setText(text)
    setSelection(text.length)
    dismissDropDown()
}

fun AutoCompleteTextView.hideKeyboard() {
    val serviceName = Context.INPUT_METHOD_SERVICE
    val inputMethodManager = context.getSystemService(serviceName) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
