package com.sulsoftware.cmp_weather.models

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Int,
    val name: String,
    val coord: Cord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)


@Serializable
data class Cord(
    val lat: Double,
    val lon: Double
)
