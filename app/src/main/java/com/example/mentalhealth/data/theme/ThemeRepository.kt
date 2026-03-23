package com.example.mentalhealth.data.theme

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.themeDataStore by preferencesDataStore(name = "theme")

enum class ThemePreference {
    SYSTEM,
    LIGHT,
    DARK,
}

class ThemeRepository(private val context: Context) {

    private val key = stringPreferencesKey("preference")

    val preference: Flow<ThemePreference> = context.themeDataStore.data.map { prefs ->
        when (prefs[key]) {
            "light" -> ThemePreference.LIGHT
            "dark" -> ThemePreference.DARK
            else -> ThemePreference.SYSTEM
        }
    }

    val nightMode: Flow<Int> = preference.map { p ->
        when (p) {
            ThemePreference.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemePreference.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemePreference.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }

    suspend fun setPreference(preference: ThemePreference) {
        context.themeDataStore.edit { prefs ->
            prefs[key] = when (preference) {
                ThemePreference.LIGHT -> "light"
                ThemePreference.DARK -> "dark"
                ThemePreference.SYSTEM -> "system"
            }
        }
    }
}
