package com.example.mentalhealth.data.session

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session")

class SessionRepository(private val context: Context) {

    private val keyLoggedIn = booleanPreferencesKey("logged_in")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[keyLoggedIn] ?: false
    }

    suspend fun setLoggedIn(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[keyLoggedIn] = value
        }
    }
}
