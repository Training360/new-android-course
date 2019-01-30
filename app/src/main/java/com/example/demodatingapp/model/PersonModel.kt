package com.example.demodatingapp.model

import com.example.demodatingapp.R

data class PersonModel(

        val id: Int,
        val avatar: Int,
        val name: String,
        val rating: Int,
        val job: String,
        val formattedAge: String,
        val introduction: String,
        val galleryImages: Array<Int>) {

    companion object {
        fun georgeClooney(): PersonModel {
            return PersonModel(
                0,
                R.drawable.user_avatar,
                "Gyula",
                4,
                "Informatikus",
                "27 éves",
                "Arra gondoltam, hogy valami jó olaszos kaja ki tudja hozni az emberek valódi énjét.\n\nSzóval... bedobhatnánk egy pizzát valamikor, szerintem az egészen király lenne.\n\nCiao!",
                arrayOf(R.drawable.clooney1, R.drawable.clooney2, R.drawable.clooney3)
            )
        }

        fun list(): List<PersonModel> {
            return listOf(
                georgeClooney(),
                PersonModel(
                    1,
                    R.drawable.henry_cavill_avatar,
                    "Henrik",
                    5,
                    "Színész",
                    "38 éves",
                    "Jelenleg itt élek Magyarországon, mivel éppen itt forgatjuk a Witcher-t a Netflixnek.\n\nMagyarul csak egy kicsit beszélek. Szeretem a marhapörköltet és a pálinkát is.\n\nSee you.",
                    arrayOf(R.drawable.henry_cavill_1, R.drawable.henry_cavill_2, R.drawable.henry_cavill_3)
                ),
                PersonModel(
                    1,
                    R.drawable.jason_momoa_avatar,
                    "Dzsézön",
                    5,
                    "Uralkodó",
                    "33 éves",
                    "Szeretek úszni, a lovakat is szeretem.\n\nNagy gyengémnek számít a már-már szürke jellegű szőke hajú hölgyeket, akik vékonyak és alacsonyak, ja és persze fiatalok.",
                    arrayOf(R.drawable.jason_momoa_1, R.drawable.jason_momoa_2)
                )
            )
        }
    }
}