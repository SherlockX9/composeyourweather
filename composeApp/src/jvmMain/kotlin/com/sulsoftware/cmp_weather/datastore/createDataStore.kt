package com.sulsoftware.cmp_weather.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import java.io.File

internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"

fun createDataStore(): DataStore<Preferences> {
    val appDataDir = File(System.getProperty("user.home"), ".cmp_weather")
    if (!appDataDir.exists()) appDataDir.mkdirs()

    return PreferenceDataStoreFactory.create(
        produceFile = { File(appDataDir, DATA_STORE_FILE_NAME) }
    )
}
