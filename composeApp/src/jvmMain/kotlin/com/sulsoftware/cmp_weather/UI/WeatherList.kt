package com.sulsoftware.cmp_weather.UI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sulsoftware.cmp_weather.models.Weather

@Composable
fun WeatherList(weather: Weather?) {
    Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        val indices = listOf(0, 2, 6, 8, 10, 13)
        indices.forEach { index ->
            weather?.let {
                if (index < it.list.size) {
                    ThreeHourWeatherUnit(it, index)
                }
            }
        }

    }
}