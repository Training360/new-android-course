package com.example.demodatingapp.vo

data class CreatePersonModel(
    private val avatar: String,
    private val name: String,
    private val rating: Int,
    private val job: String,
    private val age: Int,
    private val introduction: String,
    private val images: List<String>,
    private val lastLocation: Place,
    private val owner: String
)
