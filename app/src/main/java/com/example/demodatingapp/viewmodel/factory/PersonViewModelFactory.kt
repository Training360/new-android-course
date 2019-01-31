package com.example.demodatingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demodatingapp.repository.PersonRepository
import com.example.demodatingapp.viewmodel.PersonDetailViewModel
import com.example.demodatingapp.viewmodel.PersonListViewModel

class PersonViewModelFactory(private val repository: PersonRepository): ViewModelProvider.Factory {

    companion object {
        val INSTANCE: PersonViewModelFactory by lazy {
            PersonViewModelFactory(PersonRepository.INSTANCE)
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PersonListViewModel::class.java) -> PersonListViewModel(repository) as T
            modelClass.isAssignableFrom(PersonDetailViewModel::class.java) -> PersonDetailViewModel(repository) as T
            else -> throw IllegalArgumentException("$modelClass is not available in this factory.")
        }
    }
}