package com.sulsoftware.cmp_weather.network

import com.sulsoftware.cmp_weather.models.Location
import com.sulsoftware.cmp_weather.models.Weather
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

suspend fun getWeather(latitude: String, longitude: String, appId: String): ApiResult<Weather> {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    return try {
        val response: Weather = client.get("http://api.openweathermap.org/data/2.5/forecast") {
            url {
                parameters.append("lat", latitude)
                parameters.append("lon", longitude)
                parameters.append("units", "metric")
                parameters.append("appid", appId)
            }
        }.body()

        println(response.list[0].main)
        ApiResult.Success(response)
    } catch (e: Exception) {
        ApiResult.Error(e, e.message)
    } finally {
        client.close()
    }
}


suspend fun getLocation(location: String, appId: String): ApiResult<List<Location>> {

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    return try {
        val response: List<Location> = client.get("http://api.openweathermap.org/geo/1.0/direct") {
            url {
                parameters.append("q", location)
                parameters.append("limit", "5")
                parameters.append("appid", appId)
            }
        }.body()
        println(response)
        ApiResult.Success(response)
    } catch (e: Exception) {
        ApiResult.Error(e, e.message)
    } finally {
        client.close()
    }


}


