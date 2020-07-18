package com.aljon.module.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.telephony.TelephonyManager
import android.text.*
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.UnderlineSpan
import android.util.Base64
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import com.aljon.baseplate.common.BuildConfig
import com.aljon.module.common.widget.CustomTypefaceSpan
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.Charset
import java.util.regex.Pattern

fun String.isEmailValid(): Boolean {
    return Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern
        .CASE_INSENSITIVE
    ).matcher(this).find()
}

@SuppressLint("DefaultLocale")
fun Context.getNetworkCountryIso(): String {
    // adb shell setprop gsm.sim.operator.iso-country ph
    if (BuildConfig.DEBUG) {
        return "ph".toUpperCase()
    }
    val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return tm.networkCountryIso.capitalize()
}

@Suppress("DEPRECATION")
@SuppressWarnings("deprecation")
fun String.toHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun String.isUpperCase(): Boolean {
    return this.single().isUpperCase()
}

fun String.isLowerCase(): Boolean {
    return this.single().isLowerCase()
}

private fun String.convertImageBitmapToBase64(): String {
    val baos = ByteArrayOutputStream()
    val bitmap = BitmapFactory.decodeFile(File(this).absolutePath) ?: return ""
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return "data:image/png;base64,".plus(Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT))
}

fun String.convertStringToBase64(): String {
    val data = this.toByteArray(Charset.forName("UTF-8"))
    return Base64.encodeToString(data, Base64.DEFAULT)
}

fun String.underlineString(
    context: Context,
    fontFamily: Int,
    vararg words: String,
    clickable: (String) -> Unit
): SpannableStringBuilder {
    val ssb = SpannableStringBuilder()
    val typeface = ResourcesCompat.getFont(context, fontFamily)

    if (this.isEmpty()) {
        throw NullPointerException("text must not be empty")
    }

    if (words.isEmpty()) {
        throw NullPointerException("words must not be empty")
    }

    ssb.append(this)
    for (item in words) {
        val findIndex = this.indexOf(item)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                clickable.invoke(item)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
        }

        ssb.setSpan(
            UnderlineSpan(), findIndex,
            findIndex + item.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        typeface?.let {
            ssb.setSpan(
                CustomTypefaceSpan(it),
                findIndex,
                findIndex + item.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        ssb.setSpan(
            clickableSpan,
            findIndex,
            findIndex + item.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return ssb
}

fun String.spannableString(
    context: Context,
    textSize: Float? = 0f,
    fontFamily: Int,
    @ColorInt fontColor: Int,
    vararg words: String,
    clickable: (String) -> Unit
): SpannableStringBuilder {
    val ssb = SpannableStringBuilder()
    val typeface = ResourcesCompat.getFont(context, fontFamily)

    if (this.isEmpty()) {
        throw NullPointerException("text must not be empty")
    }

    if (words.isEmpty()) {
        throw NullPointerException("words must not be empty")
    }

    ssb.append(this)
    for (item in words) {
        val findIndex = this.indexOf(item)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                clickable?.invoke(item)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }

        ssb.setSpan(
            ForegroundColorSpan(fontColor), findIndex,
            findIndex + item.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        typeface?.let {
            ssb.setSpan(
                CustomTypefaceSpan(it),
                findIndex,
                findIndex + item.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        ssb.setSpan(
            clickableSpan,
            findIndex,
            findIndex + item.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textSize?.let {
            ssb.setSpan(
                RelativeSizeSpan(it),
                findIndex,
                findIndex + item.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }
    }
    return ssb
}
