package com.sulsoftware.cmp_weather.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<DailyWeather>,
    val city: City
)

@Serializable
data class DailyWeather(
    val dt: Int,
    val main: MainInfo,
    val weather: List<WeatherDescription>,
    val clouds: Cloud,
    val wind: Wind,
    val visibility: Int? = null,
    val pop: Double,
    val rain: Rain? = null,
    val snow: Snow? = null,
    val sys: Sys,
    @SerialName("dt_txt")
    val dateText: String
)


@Serializable
data class MainInfo(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("grnd_level")
    val grndLevel: Int,
    val humidity: Int,
    @SerialName("temp_kf")
    val tempKf: Double
)

@Serializable
data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Cloud(
    val all: Int
)

@Serializable
data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

@Serializable
data class Rain(
    @SerialName("3h")
    val threeHours: Double
)

@Serializable
data class Sys(
    val pod: String
)

@Serializable
data class Snow(
    @SerialName("3h")
    val threeHours: Double
)
