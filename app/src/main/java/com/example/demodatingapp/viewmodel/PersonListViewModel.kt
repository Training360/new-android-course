package com.example.demodatingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.demodatingapp.repository.PersonRepository

class PersonListViewModel(repository: PersonRepository): ViewModel() {
    val persons = repository.getPersons()
}