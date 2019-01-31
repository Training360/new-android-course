package com.example.demodatingapp.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter("visibleGone")
    @JvmStatic
    fun View.visibleGone(visible: Boolean) {
        visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @BindingAdapter("imageName")
    @JvmStatic
    fun ImageView.setRemoteImage(name: String) {
        ImageLoader.load(name, this)
    }

    @BindingAdapter("starCount")
    @JvmStatic
    fun TextView.starEmoji(count: Int) {
        val starEmojiHex = 0x2B50
        val starEmojiString = String(Character.toChars(starEmojiHex))
        text = starEmojiString.repeat(count)
    }

    @BindingAdapter("roundedResource")
    @JvmStatic
    fun ImageView.setRoundedResource(imageName: String?) {
        imageName?.let {
            ImageLoader.loadCircular(it, this)
        }
    }
}