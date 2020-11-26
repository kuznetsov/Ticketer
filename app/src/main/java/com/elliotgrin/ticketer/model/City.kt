package com.elliotgrin.ticketer.model

data class City(
    val _score: Int,
    val city: String,
    val clar: String,
    val country: String,
    val countryCode: String,
    val countryId: Int,
    val fullname: String,
    val hotelsCount: Int,
    val iata: List<String>,
    val id: Int,
    val isOutOfService: Boolean,
    val latinCity: String,
    val latinClar: String,
    val latinCountry: String,
    val latinFullName: String,
    val location: Location,
    val state: Any,
    val timezone: String,
    val timezonesec: Int
)

data class Location(
    val lat: Double,
    val lon: Double
)
