package com.example.demodatingapp.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.demodatingapp.R
import com.example.demodatingapp.databinding.FragmentMapBinding
import com.example.demodatingapp.network.adapter.PersonInfoWindowAdapter
import com.example.demodatingapp.util.LocationLiveData
import com.example.demodatingapp.viewmodel.MapViewModel
import com.example.demodatingapp.viewmodel.factory.PersonViewModelFactory
import com.example.demodatingapp.vo.Person
import com.example.demodatingapp.vo.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment: Fragment() {

    companion object {
        const val LOCATION_REQUEST_CODE = 1
    }

    private lateinit var binding: FragmentMapBinding

    private lateinit var viewModel: MapViewModel

    private var googleMap: GoogleMap? = null

    private var locationObserver: LocationLiveData? = null

    private lateinit var infoWindowAdapter: PersonInfoWindowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        setHasOptionsMenu(true)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync {
            googleMap = it
            googleMap!!.setOnMyLocationButtonClickListener {
                observeDeviceLocation()
                true
            }
            googleMap!!.setInfoWindowAdapter(infoWindowAdapter)

            googleMap!!.setOnInfoWindowClickListener { marker ->
                infoWindowAdapter.markers[marker.id]?.apply {
                    findNavController().navigate(MapFragmentDirections.detailFromMap(id))
                }
            }

            checkForPermissions()
            observePersons()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, PersonViewModelFactory(application()))
            .get(MapViewModel::class.java)

        infoWindowAdapter = PersonInfoWindowAdapter(requireContext())
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        when(requestCode) {
            LOCATION_REQUEST_CODE -> {
                val permission = Manifest.permission.ACCESS_FINE_LOCATION
                val granted = PackageManager.PERMISSION_GRANTED
                if (permissions.size == 1 && permissions.first() == permission &&
                    grantResults.first() == granted) {
                    setupGoogleMap()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.fragment_list) {
            findNavController().navigateUp()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupGoogleMap() {
        googleMap?.isMyLocationEnabled = true
        googleMap?.uiSettings!!.isMapToolbarEnabled = false
    }

    private fun checkForPermissions() {
        if (hasLocationPermission()) {
            setupGoogleMap()
            observeDeviceLocation()
        } else {
            val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissions, LOCATION_REQUEST_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    private fun observeDeviceLocation() {
        if (hasLocationPermission()) {
            if (locationObserver != null) {
                locationObserver?.refreshLocation()
                return
            }
            locationObserver = viewModel.currentLocation(context!!)
            locationObserver!!.observe(this, Observer {
                val deviceLocation = LatLng(it.latitude, it.longitude)
                val cameraPosition = CameraPosition.fromLatLngZoom(deviceLocation, 15f)
                googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            })
            locationObserver?.refreshLocation()

        } else {
            locationObserver?.removeObservers(this)
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            .let {
                it == PackageManager.PERMISSION_GRANTED
            }
    }

    private fun observePersons() {
        viewModel.persons.observe(this, Observer { resource ->
            googleMap!!.clear()
            val markers = HashMap<String, Person>()
            resource.data?.let { persons ->
                persons.map { person ->
                    person
                }.forEach { person ->
                    val latLng = person.lastLocation.latLng
                    val options = MarkerOptions()
                        .position(latLng)
                        .title(person.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(353f))
                    googleMap!!.addMarker(options).also {
                        markers[it.id] = person
                    }
                }
                infoWindowAdapter.markers = markers
            }
        })
    }
}

private val Place.latLng: LatLng
    get() {
        return LatLng(latitude, longitude)
    }