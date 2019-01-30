package com.example.demodatingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demodatingapp.model.PersonModel

class PersonListViewModel: ViewModel() {
    val persons = MutableLiveData<List<PersonModel>>()

    init {
        persons.value = PersonModel.list()
    }
}