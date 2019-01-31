package com.example.demodatingapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.widget.ImageView
import com.example.demodatingapp.BuildConfig
import com.example.demodatingapp.network.livedata.ConnectivityLiveData
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class ImageLoader(context: Context) {

    private val connectivityLiveData =
        ConnectivityLiveData(context.getSystemService(ConnectivityManager::class.java))

    var isOnline = false

    init {
        connectivityLiveData.observeForever {
            isOnline = it
        }
    }

    fun load(url: String, placeHolder: Int? = null, imageView: ImageView) {
        load(url, placeHolder, null, imageView)
    }

    fun loadCircular(url: String, placeHolder: Int? = null, imageView: ImageView) {
        load(url, placeHolder, CircleTransform(), imageView)
    }

    private fun load(url: String, placeHolder: Int?, circleTransform: CircleTransform?, imageView: ImageView) {
        val uri = Uri.parse("$BASE_URL/$url")
        var creator = Picasso.get().load(uri)
        placeHolder?.let { creator = creator.placeholder(placeHolder) }
        circleTransform?.let { creator = creator.transform(circleTransform) }
        if (!isOnline) {
            creator = creator.networkPolicy(NetworkPolicy.OFFLINE)
        }
        creator.into(imageView)
    }

    companion object: SingletonHolder<ImageLoader, Context>(::ImageLoader) {
        private const val BASE_URL = "${BuildConfig.BASE_URL}/static"
    }
}