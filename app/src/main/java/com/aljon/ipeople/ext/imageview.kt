package com.aljon.ipeople.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.aljon.ipeople.GlideApp
import com.aljon.ipeople.R
import com.aljon.ipeople.utils.Constants.RANDOM_AVATAR_URL
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import timber.log.Timber

fun ImageView.loadAvatarUrl(url: String?) {
    GlideApp.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_default_avatar)
        .error(R.drawable.ic_default_avatar)
        .fallback(R.drawable.ic_default_avatar)
        .skipMemoryCache(false)
        .dontAnimate()
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Timber.e("onLoadFailed Error $e")
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
        .into(this)
}

fun ImageView.loadImageUrl(url: String?) {
    GlideApp.with(this.context)
        .load(url)
        .centerCrop()
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Timber.e("onLoadFailed Error $e ")
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
        .into(this)
}

fun ImageView.loadDrawable(@DrawableRes resId: Int?) {
    resId?.let {
        GlideApp.with(context)
                .load(resId)
                .dontAnimate()
                .into(this)
    }
}

@BindingAdapter("avatar")
fun ImageView.setAvatar(avatarID: String?) {
    GlideApp.with(context)
        .load(RANDOM_AVATAR_URL + avatarID)
        .into(this)
}
