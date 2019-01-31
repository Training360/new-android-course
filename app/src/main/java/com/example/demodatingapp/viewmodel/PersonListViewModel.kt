package com.example.demodatingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.demodatingapp.repository.PersonRepository
import com.example.demodatingapp.util.RetryCallback
import com.example.demodatingapp.vo.Person
import com.example.demodatingapp.vo.Resource

class PersonListViewModel(private val repository: PersonRepository): ViewModel(), RetryCallback {

    private var repoSource: LiveData<Resource<List<Person>>>

    val persons: MediatorLiveData<Resource<List<Person>>> = MediatorLiveData()

    init {
        repoSource = repository.getPersons()
        persons.addSource(repoSource) {
            persons.value = it
        }
    }

    override fun retry() {
        persons.removeSource(repoSource)
        repoSource = repository.getPersons()
        persons.addSource(repoSource) {
            persons.value = it
        }
    }
}