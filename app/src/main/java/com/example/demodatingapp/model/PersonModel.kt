package com.example.demodatingapp.model

import com.example.demodatingapp.R

data class PersonModel(
        val avatar: Int,
        val name: String,
        val rating: Int,
        val job: String,
        val formattedAge: String,
        val image: Int,
        val introduction: String) {

    companion object {
        fun georgeClooney(): PersonModel {
            return PersonModel(
                R.drawable.user_avatar,
                "Gyula",
                4,
                "Informatikus",
                "27 éves",
                R.drawable.clooney3,
                "Arra gondoltam, hogy valami jó olaszos kaja ki tudja hozni az emberek valódi énjét.\n\nSzóval... bedobhatnánk egy pizzát valamikor, szerintem az egészen király lenne.\n\nCiao!"
            )
        }
    }
}