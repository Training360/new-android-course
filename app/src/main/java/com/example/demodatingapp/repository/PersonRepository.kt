package com.example.demodatingapp.repository

import androidx.lifecycle.LiveData
import com.example.demodatingapp.db.dao.PersonDao
import com.example.demodatingapp.network.api.JingleApi
import com.example.demodatingapp.network.api.response.ApiResponse
import com.example.demodatingapp.repository.task.NetworkBoundTask
import com.example.demodatingapp.threading.JingleExecutors
import com.example.demodatingapp.util.RateLimiter
import com.example.demodatingapp.vo.Person
import com.example.demodatingapp.vo.Resource
import java.util.concurrent.TimeUnit

class PersonRepository(private val jingleApi: JingleApi,
                       private val executors: JingleExecutors,
                       private val personDao: PersonDao
) {

    private val personListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun getPersons(): LiveData<Resource<List<Person>>> {
        val rateLimiterKey = "personList"
        return object : NetworkBoundTask<List<Person>, List<Person>>(executors) {

            override fun saveCallResult(item: List<Person>) = personDao.insert(item)

            override fun createCall(): LiveData<ApiResponse<List<Person>>> = jingleApi.getPersons()

            override fun shouldFetch(data: List<Person>?): Boolean =
                (data == null || data.isEmpty() || personListRateLimit.shouldFetch(rateLimiterKey))

            override fun loadFromDb(): LiveData<List<Person>> = personDao.findAll()

            override fun onFetchFailed() = personListRateLimit.reset(rateLimiterKey)
        }.asLiveData()
    }

    fun getPerson(id: Int): LiveData<Resource<Person>> {
        return object : NetworkBoundTask<Person, Person>(executors) {
            override fun saveCallResult(item: Person) {
                personDao.insert(item)
            }

            override fun createCall(): LiveData<ApiResponse<Person>> {
                return jingleApi.getPerson(id)
            }

            override fun shouldFetch(data: Person?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<Person> {
                return personDao.findById(id)
            }
        }.asLiveData()
    }
}