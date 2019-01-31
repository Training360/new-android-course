package com.example.demodatingapp.vo

import com.google.gson.annotations.SerializedName

data class Person(
    val id: Int,
    val avatar: String,
    val name: String,
    val rating: Int,
    val job: String,
    val age: Int,
    val introduction: String,
    @SerializedName("images") val galleryImages: Array<String>)