package com.sulsoftware.cmp_weather.UI

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.YoutubeSearchedFor
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun SettingsComponent(showSettingsDetails: Boolean, onDismissRequest: () -> Unit, prefs: DataStore<Preferences>) {

    val textFieldState = rememberTextFieldState()
    val apiValue by prefs
        .data
        .map {
            val apiKey = stringPreferencesKey("api")
            it[apiKey] ?: ""
        }
        .collectAsState("Not set")
    val scope = rememberCoroutineScope()
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onDismissRequest) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
        }
        Text(
            text = if (apiValue.isEmpty()) "Api key not set" else "Api key set",
            color = if (apiValue.isEmpty()) androidx.compose.material3.MaterialTheme.colorScheme.error
            else androidx.compose.material3.MaterialTheme.colorScheme.inversePrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
    AnimatedVisibility(showSettingsDetails) {
        Column(modifier = Modifier.padding(horizontal = 10.dp).padding(end = 10.dp)) {
            Row() {
                Text(
                    text = "Current api key: ",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                SelectionContainer {
                    Text(text = apiValue,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp)
                }
            }
            OutlinedTextField(
                state = textFieldState,
                lineLimits = TextFieldLineLimits.SingleLine,
                label = { Text(if (apiValue.isEmpty()) "Set your API key" else "Update your API key", fontSize = 12.sp) },
                leadingIcon = {
                    IconButton(onClick = { textFieldState.clearText() }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            prefs.edit { dataStore ->
                                val apiKey = stringPreferencesKey("api")
                                dataStore[apiKey] = textFieldState.text.toString()
                            }
                        }
                    }) {
                        Icon(imageVector = if (apiValue.isEmpty()) Icons.Default.ArrowCircleRight else Icons.Default.Update, contentDescription = "Update/Enter")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

    }


}