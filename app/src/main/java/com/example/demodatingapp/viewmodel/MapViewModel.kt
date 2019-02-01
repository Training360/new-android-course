package com.example.demodatingapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.demodatingapp.repository.PersonRepository
import com.example.demodatingapp.util.LocationLiveData

class MapViewModel(private val repository: PersonRepository): ViewModel() {

    fun currentLocation(context: Context): LocationLiveData {
        return LocationLiveData(context)
    }

    val persons = repository.getPersons()
}