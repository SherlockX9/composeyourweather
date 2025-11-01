package com.sulsoftware.cmp_weather.UI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sulsoftware.cmp_weather.models.Location

@Composable
fun LocationList(locations: List<Location>) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        for (location in locations) {
            Text(text = location.country, color = MaterialTheme.colorScheme.onBackground)
            Text(text = location.name, fontSize = 12.sp)
            Row() {
                Text(text = "Latitude: ", fontSize = 12.sp)
                SelectionContainer {
                    Text(text = "${location.lat}", fontWeight = FontWeight.Medium, fontSize = 12.sp)
                }
            }
            Row() {
                Text(text = "Longtitude: ", fontSize = 12.sp)
                SelectionContainer {
                    Text(text = "${location.lon}", fontWeight = FontWeight.Medium, fontSize = 12.sp)
                }
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp))
        }
    }
}