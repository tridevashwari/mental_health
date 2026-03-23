package com.example.mentalhealth.data.mood

import com.example.mentalhealth.data.local.MoodEntry
import com.example.mentalhealth.data.local.MoodEntryDao
import kotlinx.coroutines.flow.Flow

class MoodRepository(private val dao: MoodEntryDao) {

    fun observeMoodLogCount(): Flow<Int> = dao.observeCount()

    suspend fun logMood(score: Int, note: String? = null) {
        require(score in 1..5) { "score must be 1..5" }
        dao.insert(MoodEntry(moodScore = score, note = note))
    }
}
