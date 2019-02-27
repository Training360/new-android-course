package com.example.demodatingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.demodatingapp.repository.PersonRepository
import com.example.demodatingapp.vo.Person
import com.example.demodatingapp.vo.Resource

class PersonDetailViewModel(private val repository: PersonRepository): ViewModel() {

    private var repoSource: LiveData<Resource<Person>>

    val user: MediatorLiveData<Resource<Person>> = MediatorLiveData()

    var userId = 0
        set(value) {
            if (field != value) {
                field = value
                retry()
            }
        }

    val email = "george@clooney.com"

    init {
        repoSource = repository.getPerson(userId)
        user.addSource(repoSource) {
            user.value = it
        }
    }

    fun retry() {
        user.removeSource(repoSource)
        repoSource = repository.getPerson(userId)
        user.addSource(repoSource) {
            user.value = it
        }
    }

}