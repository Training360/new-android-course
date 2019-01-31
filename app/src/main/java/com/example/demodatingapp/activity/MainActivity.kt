package com.example.demodatingapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.demodatingapp.R
import com.example.demodatingapp.databinding.ActivityMainBinding
import com.example.demodatingapp.network.livedata.ConnectivityLiveData
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private lateinit var noInternetSnack: Snackbar
    private lateinit var connectivityLiveData: ConnectivityLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.jingle_nav_fragment)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)

        noInternetSnack = Snackbar.make(binding.root, R.string.no_internet_error, Snackbar.LENGTH_INDEFINITE)
        connectivityLiveData = ConnectivityLiveData(this)
        connectivityLiveData.observe(this, Observer {
            if (it) {
                noInternetSnack.dismiss()
            } else {
                if (!noInternetSnack.isShown) {
                    noInternetSnack.show()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
