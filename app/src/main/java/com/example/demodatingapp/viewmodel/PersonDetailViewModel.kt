package com.example.demodatingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.demodatingapp.repository.PersonRepository
import com.example.demodatingapp.vo.Person
import com.example.demodatingapp.vo.Resource

class PersonDetailViewModel(private val repository: PersonRepository): ViewModel() {

    fun getUser(index: Int): LiveData<Resource<Person>> {
        return repository.getPerson(index)
    }

}