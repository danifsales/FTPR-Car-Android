package com.dani.ftprcar.data
data class Place(val lat: Double, val long: Double)
data class Car(
    val id: String? = null,
    val imageUrl: String,
    val year: String,
    val name: String,
    val licence: String,
    val place: Place
)
