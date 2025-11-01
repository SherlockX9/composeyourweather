package com.sulsoftware.cmp_weather

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sulsoftware.cmp_weather.datastore.DATA_STORE_FILE_NAME
import com.sulsoftware.cmp_weather.datastore.createDataStore

fun main() {
    val prefs = createDataStore()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "cmp_weather",
        ) {
            App(
                prefs = prefs
            )
        }
    }
}