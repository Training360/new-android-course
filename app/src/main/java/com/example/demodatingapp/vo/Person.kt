package com.example.demodatingapp.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    indices = [
        Index("id")
    ],
    primaryKeys = ["id"]
)
data class Person(
    val id: Int,
    val avatar: String,
    val name: String,
    val rating: Int,
    val job: String,
    val age: Int,
    val introduction: String,
    @SerializedName("images") val galleryImages: Array<String>,
    @Embedded val lastLocation: Place,
    val owner: String
)

data class Place(val latitude: Double, val longitude: Double): Serializable