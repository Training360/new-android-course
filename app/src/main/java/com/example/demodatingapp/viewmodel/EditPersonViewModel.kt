package com.example.demodatingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.demodatingapp.repository.PersonRepository
import com.example.demodatingapp.util.ImageUploader
import com.example.demodatingapp.vo.Person
import com.example.demodatingapp.vo.Resource
import java.io.File

class EditPersonViewModel(private val personRepository: PersonRepository): ViewModel() {

    private val trigger = MutableLiveData<Boolean>()

    lateinit var fields: PersonFields

    val updatePerson: LiveData<Resource<Person>> = Transformations.switchMap(trigger) {
        val owner = "george@clooney.com"

        val person = Person(
                fields.id,
                fields.bigPhotoName!!,
                fields.displayName!!,
                fields.rating,
                fields.job,
                fields.age,
                fields.introduction,
                arrayOf(fields.bigPhotoName!!),
                fields.location!!,
                owner!!
        )

        return@switchMap personRepository.updatePerson(person)
    }

    fun retry() {
        trigger.value = trigger.value ?: false
    }

    fun validate(): Boolean {
        return fields.validate()
    }

    fun upload(file: File, callback: (String?) -> Unit) {
        ImageUploader.upload(file, callback)
    }
}