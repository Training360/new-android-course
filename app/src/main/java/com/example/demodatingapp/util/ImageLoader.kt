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

    fun loadCircular(url: String, placeHolder: Int? = null, errorImage: Int? = null, imageView: ImageView) {
        val uri = Uri.parse("$BASE_URL/$url")
        load(uri, placeHolder, errorImage, CircleTransform(), imageView)
    }

    fun loadCircular(url: Uri, placeHolder: Int, errorImage: Int, imageView: ImageView) {
        load(url, placeHolder, errorImage, CircleTransform(), imageView)
    }

    fun load(url: String, placeHolder: Int?, errorImage: Int?, imageView: ImageView) {
        val uri = Uri.parse("$BASE_URL/$url")
        load(uri, placeHolder, errorImage, null, imageView)
    }

    private fun load(url: Uri, placeHolder: Int?, errorImage: Int?, circleTransform: CircleTransform?, imageView: ImageView) {
        var creator = Picasso.get().load(url)
        placeHolder?.let { creator = creator.placeholder(placeHolder) }
        circleTransform?.let { creator = creator.transform(circleTransform) }
        errorImage?.let { creator = creator.error(errorImage) }
        if (!isOnline) {
            creator = creator.networkPolicy(NetworkPolicy.OFFLINE)
        }
        creator.into(imageView)
    }

    companion object: SingletonHolder<ImageLoader, Context>(::ImageLoader) {
        private const val BASE_URL = "${BuildConfig.BASE_URL}/static"
    }
}