package com.sulsoftware.cmp_weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sulsoftware.cmp_weather.UI.LocationComponent
import com.sulsoftware.cmp_weather.UI.SettingsComponent
import com.sulsoftware.cmp_weather.UI.WeatherComponent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    prefs: DataStore<Preferences>
) {
    var showSettingsDetails by remember {mutableStateOf(false)}
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f).padding(vertical = 20.dp).padding(start = 10.dp)) {
                    SettingsComponent(
                        showSettingsDetails, { showSettingsDetails = !showSettingsDetails }, prefs
                    )
                }
                Text(
                    text = "Compose Weather.",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 20.dp).weight(1f),
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 38.sp
                )
                Spacer(modifier = Modifier.weight(0.4f))
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Column(modifier = Modifier.weight(1f).padding(horizontal = 20.dp)) {
                    LocationComponent(prefs = prefs)
                }
                Column(modifier = Modifier.weight(1f).padding(horizontal = 20.dp)) {
                    WeatherComponent(prefs = prefs)
                }
            }
        }
    }
}