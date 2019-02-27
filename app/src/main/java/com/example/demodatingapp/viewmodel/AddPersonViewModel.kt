package com.example.demodatingapp.viewmodel

import android.graphics.Bitmap
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import com.example.demodatingapp.BR
import com.example.demodatingapp.R
import com.example.demodatingapp.vo.Person
import com.example.demodatingapp.vo.Place

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

    val photoUrl = currentUser!!.galleryImages.get(0)

    var bitmap: Bitmap? = null

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

class AddPersonViewModel : ViewModel() {
    fun validate(): Boolean {
        return personFields.validate()
    }

    val personFields = PersonFields()
}