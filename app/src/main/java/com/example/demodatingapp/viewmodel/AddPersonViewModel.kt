package com.example.demodatingapp.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.demodatingapp.BR
import com.example.demodatingapp.R
import com.example.demodatingapp.network.api.JingleApi
import com.example.demodatingapp.repository.PersonRepository
import com.example.demodatingapp.threading.JingleExecutors
import com.example.demodatingapp.vo.CreatePersonModel
import com.example.demodatingapp.vo.Person
import com.example.demodatingapp.vo.Place
import com.example.demodatingapp.vo.Resource
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.IOException

class PersonFields(): BaseObservable() {

    private var currentUser = Person(
        0,
        "clooney_avatar.png",
        "György",
        4,
        "Informatikus",
        27,
        "Arra gondoltam, hogy valami jó olaszos kaja ki tudja hozni az emberek valódi énjét.",
        arrayOf("clooney2.png", "clooney3.png"),
        Place(52.232357, 21.152927)
    )

    @Bindable
    var job = String()
        set(value) {
            if (field != value) {
                field = value
                validate()
                notifyPropertyChanged(BR.job)
                notifyPropertyChanged(BR.jobError)
            }
        }

    @Bindable
    var rating = 0
        set(value) {
            if (field != value) {
                field = value
                validate()
                notifyPropertyChanged(BR.rating)
                notifyPropertyChanged(BR.ratingError)
            }
        }

    @Bindable
    var age = 0
        set(value) {
            if (field != value) {
                field = value
                validate()
                notifyPropertyChanged(BR.age)
                notifyPropertyChanged(BR.ageError)
            }
        }

    @Bindable
    var introduction = String()
        set(value) {
            if (field != value) {
                field = value
                validate()
                notifyPropertyChanged(BR.introduction)
                notifyPropertyChanged(BR.introError)
            }
        }

    val displayName = currentUser!!.name

    var bigPhotoName: String? = null

    @Bindable
    var jobError = 0
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.jobError)
            }
        }

    @Bindable
    var ageError = 0
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.ageError)
            }
        }

    @Bindable
    var ratingError = 0
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.ratingError)
            }
        }

    @Bindable
    var introError = 0
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.introError)
            }
        }

    var location: Place? = null

    fun validate(): Boolean {
        var result = true
        if (age !in 18..99) {
            ageError = R.string.person_age_error
            result = false
        } else {
            ageError = 0
        }
        if (rating !in 1..5) {
            ratingError = R.string.person_rating_error
            result = false
        } else {
            ratingError = 0
        }
        if (job.isEmpty()) {
            jobError = R.string.person_job_error
            result = false
        } else {
            jobError = 0
        }
        if (introduction.length < 50) {
            introError = R.string.person_introduction_error
            result = false
        } else {
            introError = 0
        }
        return result
    }
}

class AddPersonViewModel(private val personRepository: PersonRepository) : ViewModel() {
    fun validate(): Boolean {
        return personFields.validate()
    }

    private val trigger = MutableLiveData<Boolean>()

    val addPerson: LiveData<Resource<List<Person>>> = Transformations
        .switchMap(trigger) {
            val person = CreatePersonModel(personFields.bigPhotoName!!,
                personFields.displayName!!,
                personFields.rating,
                personFields.job,
                personFields.age,
                personFields.introduction,
                listOf(personFields.bigPhotoName!!),
                personFields.location!!,
                "noone@email.com"
            )
            return@switchMap personRepository.addPerson(person)
        }


    fun upload(file: File, callback: (String?) -> Unit) {
        JingleExecutors.INSTANCE.networkIO.execute {
            JingleApi.uploadImage(file).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    JingleExecutors.INSTANCE.mainThread.execute {
                        callback(null)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    JingleExecutors.INSTANCE.mainThread.execute {
                        if (response.isSuccessful) {
                            val fileName = file.name
                            personFields.bigPhotoName = fileName
                            callback(fileName)
                        }
                    }
                }
            })
        }
    }

    fun retry() {
        trigger.value.let {
            if (it == null) {
                trigger.value = false
            } else {
                trigger.value = !it
            }
        }
    }

    val personFields = PersonFields()
}