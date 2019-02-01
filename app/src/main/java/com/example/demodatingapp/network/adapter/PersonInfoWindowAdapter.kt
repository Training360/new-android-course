package com.example.demodatingapp.network.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.demodatingapp.R
import com.example.demodatingapp.vo.Person
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class PersonInfoWindowAdapter(val context: Context): GoogleMap.InfoWindowAdapter {

    var markers: Map<String, Person> = HashMap()

    override fun getInfoWindow(marker: Marker?): View? {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<com.example.demodatingapp.databinding.ItemMarkerBinding>(inflater, R.layout.item_marker, null, false)
        val person = markers[marker!!.id]
        binding.person = person
        binding.executePendingBindings()
        return binding.root
    }

    override fun getInfoContents(marker: Marker?): View? {
        return null
    }
}