package com.sulsoftware.cmp_weather.UI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sulsoftware.cmp_weather.network.ApiResult
import com.sulsoftware.cmp_weather.network.getWeather
import com.sulsoftware.cmp_weather.models.Weather
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun WeatherComponent(prefs: DataStore<Preferences>) {
    val latitudeTextFieldState = rememberTextFieldState()
    val longitudeTextFieldState = rememberTextFieldState()
    var weather by remember { mutableStateOf<Weather?>(null) }
    var errorMessage by remember { mutableStateOf("") }
    var showErrorMessage by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val apiValue by prefs
        .data
        .map {
            val apiKey = stringPreferencesKey("api")
            it[apiKey] ?: ""
        }
        .collectAsState("")
    OutlinedTextField(
        state = latitudeTextFieldState,
        label = { Text(text = "Enter latitude", fontSize = 12.sp) },
        trailingIcon = {
            IconButton(onClick = {
                latitudeTextFieldState.clearText()
            }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        state = longitudeTextFieldState, label = { Text("Enter longitude", fontSize = 12.sp) },
        trailingIcon = {
            IconButton(onClick = {
                longitudeTextFieldState.clearText()
            }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
            }
        }, modifier = Modifier.fillMaxWidth()
    )

    Button(onClick = {
        scope.launch {
            when (val result =
                getWeather(latitudeTextFieldState.text.toString(), longitudeTextFieldState.text.toString(), apiValue)) {
                is ApiResult.Success -> {
                    weather = result.data
                    showErrorMessage = false
                }

                is ApiResult.Error -> {
                    showErrorMessage = true
                    errorMessage = result.message ?: "Unexpected error."
                    println("API ERROR ${result.message}")
                }
            }
        }
    }, shape = RectangleShape, modifier = Modifier.padding(vertical = 5.dp).padding(bottom = 10.dp)) {
        Text("Get weather")
    }
    when(showErrorMessage) {
        true -> Column() {Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)}
        false -> WeatherList(weather)
    }
}