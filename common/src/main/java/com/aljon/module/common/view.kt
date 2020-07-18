package com.aljon.module.common

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

/**
 * @author ceosilvajr
 */
fun View.doubleClick(onComplete: () -> Unit): Disposable {
    val timeout = 400L
    val timeUnits = TimeUnit.MILLISECONDS
    val observable = this.clicks().share()
    return observable.buffer(observable.debounce(timeout, timeUnits))
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            if (it.size == 2) onComplete.invoke()
        }
}

/** Milliseconds used for UI animations */
const val ANIMATION_FAST_MILLIS = 50L
const val ANIMATION_SLOW_MILLIS = 100L
const val NINJA_TAP_THROTTLE_TIME = 400L

/** Combination of all flags required to put activity into immersive mode */
const val FLAGS_FULLSCREEN =
    (View.SYSTEM_UI_FLAG_LOW_PROFILE or
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
        or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
        or View.SYSTEM_UI_FLAG_IMMERSIVE
        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

const val NORMAL_SCREEN = (View.SYSTEM_UI_FLAG_LOW_PROFILE or
    View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

fun View.toggleSystemUiVisibility() {
    var uiOptions = this.systemUiVisibility
    uiOptions = uiOptions and View.SYSTEM_UI_FLAG_LOW_PROFILE.inv()
    uiOptions = uiOptions or View.SYSTEM_UI_FLAG_FULLSCREEN
    uiOptions = uiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    uiOptions = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE
    uiOptions = uiOptions and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
    this.systemUiVisibility = uiOptions
}

fun View.setVisible(isVisible: Boolean?) {
    if (isVisible == true && visibility == View.VISIBLE) {
        return
    }

    if (isVisible == false && visibility == View.GONE) {
        return
    }

    this.visibility = when (isVisible) {
        false -> View.GONE
        else -> View.VISIBLE
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.setHidden(isHidden: Boolean) {
    this.visibility = if (isHidden) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

inline fun View.enableWhen(editText: EditText, crossinline predicate: (CharSequence) -> Boolean) {
    editText.addTextChangedListener(object : TextWatcher {

        override fun onTextChanged(input: CharSequence, start: Int, before: Int, count: Int) {
            isEnabled = if (input.isNotEmpty()) {
                predicate(input)
            } else {
                false
            }
        }

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    })
    editText.text = editText.text // Force trigger
}

fun View.ninjaTap(onNext: (View) -> Unit): Disposable {
    return this.clicks().throttleFirst(NINJA_TAP_THROTTLE_TIME, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(
            onNext = {
                onNext.invoke(this)
            }
        )
}
