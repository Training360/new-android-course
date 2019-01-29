package com.example.demodatingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demodatingapp.model.PersonModel

class PersonDetailViewModel: ViewModel() {
    private lateinit var person: MutableLiveData<PersonModel>

    fun getUser(): MutableLiveData<PersonModel> {
        if(!::person.isInitialized) {
            person = MutableLiveData()
            person.value = PersonModel.georgeClooney()
        }
        return person
    }
}