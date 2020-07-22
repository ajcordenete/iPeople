package com.aljon.module.common

import android.app.Activity
import android.content.res.Resources
import android.os.Environment
import android.util.DisplayMetrics
import android.view.View
import android.view.Window.ID_ANDROID_CONTENT
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.aljon.baseplate.common.R
import java.io.File
import java.io.IOException

fun getStatusBarHeight(): Int {
    val res = Resources.getSystem()
    val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        return res.getDimensionPixelSize(resourceId)
    }

    return 0
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showOkDialog(
    dialogTitle: String,
    dialogText: String,
    actionPositiveText: Int,
    action: () -> Unit = {}
) {
    AlertDialog.Builder(this)
        .setTitle(dialogTitle)
        .setMessage(dialogText)
        .setCancelable(true)
        .setPositiveButton(actionPositiveText) { _, _ ->
            action()
        }
        .create()
        .show()
}

fun Activity.getDeviceHeight(): Int {
    return DisplayMetrics().apply {
        this@getDeviceHeight.windowManager.defaultDisplay.getMetrics(this)
    }.heightPixels
}

fun Activity.getDeviceWidth(): Int {
    return DisplayMetrics().apply {
        this@getDeviceWidth.windowManager.defaultDisplay.getMetrics(this)
    }.heightPixels
}

fun Activity.convertPxToDp(px: Float): Float {
    return px / this.resources.displayMetrics.density
}

fun Activity.convertDpToPx(dp: Float): Float {
    return dp * this.resources.displayMetrics.density
}

@Throws(IOException::class)
fun Activity.createImageFile(): File {
    val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

    return File.createTempFile(
            "avatar", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
    )
}

fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView = this.getRootView()
    val heightDiff = rootView.height - rootView.height
    val contentViewTop = window.findViewById<View>(ID_ANDROID_CONTENT).top

    return heightDiff > contentViewTop
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}

fun Activity.showAlertDialog(
    dialogTitle: Int,
    dialogText: Int,
    actionPositiveText: Int,
    actionPositive: () -> Unit,
    actionNegativeText: Int = R.string.cancel,
    actionNegative: () -> Unit = {}
) {
    AlertDialog.Builder(this)
        .setTitle(dialogTitle)
        .setMessage(dialogText)
        .setCancelable(true)
        .setNegativeButton(R.string.cancel, null)
        .setPositiveButton(actionPositiveText) { _, _ ->
            actionPositive()
        }
        .setNegativeButton(actionNegativeText) { _, _ ->
            actionNegative()
        }
        .create()
        .show()
}
