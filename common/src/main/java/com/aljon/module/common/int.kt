package com.aljon.module.common

import android.content.Context
import java.util.*

fun IntRange.random() =
        Random().nextInt((endInclusive + 1) - start) + start

fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()
