package com.example.demodatingapp.network.livedata

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData

class ConnectivityLiveData(private val connectivityManager: ConnectivityManager): LiveData<Boolean>() {

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    internal constructor(context: Context) : this(
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network?) {
            postValue(false)
        }

        override fun onAvailable(network: Network?) {
            postValue(true)
        }
    }

    override fun onActive() {
        super.onActive()
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnectedOrConnecting == true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}