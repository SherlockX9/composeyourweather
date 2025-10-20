package com.sulsoftware.cmp_weather.UI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sulsoftware.cmp_weather.models.Weather

@Composable
fun ThreeHourWeatherUnit(weather: Weather, dayAndTimeUnit: Int) {
    val rainProbability = weather.list[dayAndTimeUnit].pop
    val weatherDescription = weather.list[dayAndTimeUnit].weather[0].description
    val dateAndTime = weather.list[dayAndTimeUnit].dateText
    val temperature = weather.list[dayAndTimeUnit].main.temp


    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = dateAndTime, color = MaterialTheme.colorScheme.secondary)
        Text(text = "Temperature: ${temperature.toString()}°C", fontSize = 12.sp)
        Text("Chance of rain: ${(rainProbability*100).toString()}%", fontSize = 12.sp)
        Text("General weather info: $weatherDescription", fontSize = 12.sp)
        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp).padding(vertical = 6.dp))
    }

}