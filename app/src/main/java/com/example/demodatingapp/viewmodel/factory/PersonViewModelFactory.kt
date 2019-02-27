package com.example.demodatingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demodatingapp.JingleApplication
import com.example.demodatingapp.network.api.JingleApi
import com.example.demodatingapp.repository.PersonRepository
import com.example.demodatingapp.threading.JingleExecutors
import com.example.demodatingapp.viewmodel.AddPersonViewModel
import com.example.demodatingapp.viewmodel.MapViewModel
import com.example.demodatingapp.viewmodel.PersonDetailViewModel
import com.example.demodatingapp.viewmodel.PersonListViewModel

class PersonViewModelFactory(private val application: JingleApplication): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val repository = PersonRepository(
            JingleApi.INSTANCE, JingleExecutors(),
            application.personDao())

        return when {
            modelClass.isAssignableFrom(PersonListViewModel::class.java) -> PersonListViewModel(repository) as T
            modelClass.isAssignableFrom(PersonDetailViewModel::class.java) -> PersonDetailViewModel(repository) as T
            modelClass.isAssignableFrom(MapViewModel::class.java) -> MapViewModel(repository) as T
            modelClass.isAssignableFrom(AddPersonViewModel::class.java) -> AddPersonViewModel(repository) as T
            else -> throw IllegalArgumentException("$modelClass is not available in this factory.")
        }
    }
}