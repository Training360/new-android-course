package com.example.demodatingapp.util

import android.net.Uri
import android.widget.ImageView
import com.example.demodatingapp.BuildConfig
import com.squareup.picasso.Picasso

class ImageLoader {
    companion object {

        private const val BASE_URL = "${BuildConfig.BASE_URL}/static"

        fun load(url: String, imageView: ImageView) {
            val uri = Uri.parse("$BASE_URL/$url")
            Picasso.get().load(uri).into(imageView)
        }

        fun loadCircular(url: String, imageView: ImageView) {
            val uri = Uri.parse("$BASE_URL/$url")
            Picasso.get().load(uri).transform(CircleTransform()).into(imageView)
        }
    }
}