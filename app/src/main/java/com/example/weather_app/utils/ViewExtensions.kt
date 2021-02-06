package com.example.weather_app.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop

fun ImageView?.loadImage(
    url: Any?,
    @DrawableRes placeholder: Int? = null,
    placeholderDrawable: Drawable? = null,
) {
    this?.context?.let {
        Glide.with(it)
            .load(url)
            .thumbnail(0.2f)
            .into(this)
    }
}


fun ImageView.loadCircularImage(url: String?, placeholderDrawable: Drawable? = null) {
    Glide.with(this).load(url)
        .transform(MultiTransformation<Bitmap>(CenterCrop(), CircleCrop()))
        .placeholder(placeholderDrawable)
        .into(this)
}

fun View.showMe() {
    this.visibility = View.VISIBLE
}

fun View.secretMe() {
    this.visibility = View.GONE
}

fun View.hideMe() {
    this.visibility = View.INVISIBLE
}

fun ViewGroup.generateBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.RGB_565)
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    return bitmap
}
