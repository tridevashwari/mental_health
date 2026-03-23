package com.example.mentalhealth

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.mentalhealth.data.journal.JournalRepository
import com.example.mentalhealth.data.local.AppDatabase
import com.example.mentalhealth.data.mood.MoodRepository
import com.example.mentalhealth.data.session.SessionRepository
import com.example.mentalhealth.data.theme.ThemeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MentalHealthApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    val database by lazy { AppDatabase.getInstance(this) }
    val sessionRepository by lazy { SessionRepository(this) }
    val moodRepository by lazy { MoodRepository(database.moodEntryDao()) }
    val journalRepository by lazy { JournalRepository(database.journalDao()) }
    val themeRepository by lazy { ThemeRepository(this) }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            themeRepository.nightMode.collect { mode ->
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }
}
