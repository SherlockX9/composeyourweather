package com.sulsoftware.cmp_weather.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sulsoftware.cmp_weather.models.Weather
import com.sulsoftware.cmp_weather.network.ApiResult
import com.sulsoftware.cmp_weather.network.getWeather
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherComponent(prefs: DataStore<Preferences>) {
    val apiValue by prefs
        .data
        .map {
            val apiKey = stringPreferencesKey("api")
            it[apiKey] ?: ""
        }
        .collectAsState("")
    val latValue by prefs
        .data
        .map {
            val latKey = stringPreferencesKey("lat")
            it[latKey] ?: ""
        }
        .collectAsState("")
    val longValue by prefs
        .data
        .map {
            val longKey = stringPreferencesKey("long")
            it[longKey] ?: ""
        }
        .collectAsState("")

    val latitudeTextFieldState = rememberTextFieldState(initialText = latValue)
    val longitudeTextFieldState = rememberTextFieldState(initialText = longValue)
    var weather by remember { mutableStateOf<Weather?>(null) }
    var errorMessage by remember { mutableStateOf("") }
    var showErrorMessage by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(latValue, longValue) {
        latitudeTextFieldState.edit { replace(0, length, latValue) }
        longitudeTextFieldState.edit { replace(0, length, longValue) }
    }

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
        }, modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
    )
    OutlinedButton(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(vertical = 10.dp),
        contentPadding = PaddingValues(5.dp),
        onClick = {
            scope.launch {
                when (val result =
                    getWeather(
                        latitudeTextFieldState.text.toString(),
                        longitudeTextFieldState.text.toString(),
                        apiValue
                    )) {
                    is ApiResult.Success -> {
                        weather = result.data
                        showErrorMessage = false
                        prefs.edit { dataStore ->
                            val latKey = stringPreferencesKey("lat")
                            dataStore[latKey] = latitudeTextFieldState.text.toString()
                            val longKey = stringPreferencesKey("long")
                            dataStore[longKey] = longitudeTextFieldState.text.toString()
                        }
                    }

                    is ApiResult.Error -> {
                        showErrorMessage = true
                        errorMessage = result.message ?: "Unexpected error."
                        println("API ERROR ${result.message}")
                    }
                }
            }
        })
    {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.width(115.dp).height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Get weather")
            TooltipBox(
                positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                tooltip = {
                    RichTooltip(
                        title = { Text("Fun fact") }
                    ) {
                        Text("The most current values in the coordinate search fields are saved on exit.")
                    }
                },
                state = rememberTooltipState()
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Show more information"
                )
            }
        }

    }
    when (showErrorMessage) {
        true -> Column() { Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp) }
        false -> WeatherList(weather)
    }
}