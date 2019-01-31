package com.example.demodatingapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.demodatingapp.network.api.JingleApi
import com.example.demodatingapp.network.api.response.ApiErrorResponse
import com.example.demodatingapp.network.api.response.ApiSuccessResponse
import com.example.demodatingapp.vo.Person
import com.example.demodatingapp.vo.Resource

class PersonRepository(private val jingleApi: JingleApi) {

    companion object {
        val INSTANCE: PersonRepository by lazy {
            PersonRepository(JingleApi.INSTANCE)
        }
    }

    fun getPersons(): LiveData<Resource<List<Person>>> {
        val result = MediatorLiveData<Resource<List<Person>>>()
        val networkSource = jingleApi.getPersons()
        result.addSource(networkSource) {
            when(it) {
                is ApiSuccessResponse -> result.postValue(Resource.success(it.body))
                is ApiErrorResponse -> result.postValue(Resource.error(it.errorMessage, null))
            }
        }
        return result
    }

    fun getPerson(id: Int): LiveData<Resource<Person>> {
        val result = MediatorLiveData<Resource<Person>>()
        val networkSource = jingleApi.getPerson(id)
        result.addSource(networkSource) {
            when(it) {
                is ApiSuccessResponse -> result.postValue(Resource.success(it.body))
                is ApiErrorResponse -> result.postValue(Resource.error(it.errorMessage, null))
            }
        }
        return result
    }
}