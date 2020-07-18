package com.aljon.ipeople.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.DrawableRes

fun EditText.setDrawableEnd(@DrawableRes drawableId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(
        0,
        0,
        drawableId,
        0
    )
}

fun EditText.indicatorIcons(@DrawableRes enabledIcon: Int, @DrawableRes disabled: Int) {
    addTextChangedListener(object : TextWatcher {

        override fun onTextChanged(input: CharSequence, start: Int, before: Int, count: Int) {
            if (input.isNotEmpty()) {
                setDrawableEnd(enabledIcon)
            } else {
                setDrawableEnd(disabled)
            }
        }

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    })
}
