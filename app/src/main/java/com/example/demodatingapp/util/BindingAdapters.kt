package com.example.demodatingapp.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.demodatingapp.R

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
        ImageLoader.getInstance(context).load(name, R.drawable.placeholder, R.drawable.error_image, this)
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
            ImageLoader.getInstance(context).loadCircular(it, R.drawable.placeholder, R.drawable.error_image, this)
        }
    }
}