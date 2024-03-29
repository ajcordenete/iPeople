package com.aljon.module.common.widget

import android.text.method.PasswordTransformationMethod
import android.view.View

class CustomPasswordTransformation : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return PasswordCharSequence(super.getTransformation(source, view))
    }

    private class PasswordCharSequence(
        val transformation: CharSequence
    ) : CharSequence by transformation {
        private val DOT = '\u2022'
        private val BIGGER_DOT = '●'

        override fun get(index: Int): Char = if (transformation[index] == DOT) {
            BIGGER_DOT
        } else {
            transformation[index]
        }
    }
}
