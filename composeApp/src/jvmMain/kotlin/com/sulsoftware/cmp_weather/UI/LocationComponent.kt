package com.sulsoftware.cmp_weather.UI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sulsoftware.cmp_weather.models.Location
import com.sulsoftware.cmp_weather.network.ApiResult
import com.sulsoftware.cmp_weather.network.getLocation
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@Composable
fun LocationComponent(prefs: DataStore<Preferences>) {
    val textFieldState = rememberTextFieldState()
    var locations by remember { mutableStateOf(emptyList<Location>()) }
    var errorMessage by remember { mutableStateOf("") }
    var showErrorMessage by remember { mutableStateOf(false) }
    val apiValue by prefs
        .data
        .map {
            val apiKey = stringPreferencesKey("api")
            it[apiKey] ?: ""
        }
        .collectAsState("")
    val scope = rememberCoroutineScope()
    Row(modifier = Modifier.padding(bottom = 20.dp)) {
        OutlinedTextField(
            state = textFieldState,
            lineLimits = TextFieldLineLimits.SingleLine,
            label = { Text("Enter city to get coordinates", fontSize = 12.sp) },
            leadingIcon = {
                IconButton(onClick = { textFieldState.clearText() }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    scope.launch {
                        when (val result = getLocation(textFieldState.text.toString(), apiValue)) {
                            is ApiResult.Success -> {
                                showErrorMessage = false
                                locations = result.data
                            }

                            is ApiResult.Error -> {
                                showErrorMessage = true
                                errorMessage =
                                    result.message ?: "Unexpected error please try again."
                                println("API_ERROR ${result.message}")
                            }
                        }
                    }
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
    when (showErrorMessage) {
        true -> Column() { Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp) }
        false -> LocationList(locations)
    }
}